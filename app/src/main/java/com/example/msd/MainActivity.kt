package com.example.msd

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.msd.ui.theme.MsdTheme
import com.example.msd.ui.theme.getTypography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        enableEdgeToEdge()
        setContent {
            // State is now managed in MyApp, but MsdTheme needs to wrap it.
            // MyApp will internally wrap its content with MsdTheme.
            MyApp()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Pill Reminders"
            val descriptionText = "Channel for pill reminder notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("pill_reminders", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun MyApp() {
    // 1. Hoist the theme and font size state here
    var darkTheme by remember { mutableStateOf(false) }
    var fontSize by remember { mutableStateOf(16f) }
    val typography = getTypography(baseFontSize = fontSize)
    // 2. Apply the theme at this top level
    MsdTheme(darkTheme = darkTheme,
                typography = typography
    ) {
        val context = LocalContext.current
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(navController = navController)
            }
            composable("main") {
                // Pass the state and callbacks down to MainScreen
                MainScreen(
                    mainNavController = navController,
                    darkTheme = darkTheme,
                    onThemeChange = { darkTheme = it },
                    fontSize = fontSize,
                    onFontSizeChange = { fontSize = it }
                )
            }
            // 3. Add a new route for the SettingsScreen
            composable("settings") {
                SettingsScreen(
                    darkTheme = darkTheme,
                    onThemeChange = { darkTheme = it },
                    fontSize = fontSize,
                    onFontSizeChange = { fontSize = it }
                )
            }
            composable("calendar") {
                CalendarScreen(
                    onBackPressed = { navController.popBackStack() },
                    onSubmit = { date, pillName ->
                        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(date))
                        val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                        PillRepository.addPill(Pill(0, pillName, formattedDate, formattedTime))
                        Toast.makeText(context, "Pill '$pillName' saved!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                )
            }
            composable("patient") {
                PatientScreen(
                    mainNavController = navController,
                    darkTheme = darkTheme,
                    onThemeChange = { darkTheme = it },
                    fontSize = fontSize,
                    onFontSizeChange = { fontSize = it }
                )
            }

            composable(
                "edit_pill/{pillId}",
                arguments = listOf(navArgument("pillId") { type = NavType.LongType })
            ) {
                val pillId = it.arguments?.getLong("pillId")
                val pill = PillRepository.getPillById(pillId!!)
                if (pill != null) {
                    EditPillScreen(
                        pill = pill,
                        onPillUpdated = { updatedPill ->
                            PillRepository.updatePill(updatedPill)
                            Toast.makeText(context, "Pill '${updatedPill.name}' updated!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        onBackPressed = { navController.popBackStack() }
                    )
                }
            }
            composable("add_reminder") {
                AddReminderScreen(
                    onAddReminder = { pillName, hour, minute ->
                        val time = String.format("%02d:%02d", hour, minute)
                        val newReminder = ReminderRepository.addReminder(Reminder(0, pillName, time))
                        scheduleNotification(context, newReminder)
                        Toast.makeText(context, "Reminder for '$pillName' saved!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    onBackPressed = { navController.popBackStack() }
                )
            }
            composable(
                "edit_reminder/{reminderId}",
                arguments = listOf(navArgument("reminderId") { type = NavType.LongType })
            ) {
                val reminderId = it.arguments?.getLong("reminderId")
                val reminder = ReminderRepository.getReminderById(reminderId!!)
                if (reminder != null) {
                    EditReminderScreen(
                        reminder = reminder,
                        onReminderUpdated = { updatedReminder ->
                            cancelNotification(context, reminder)
                            ReminderRepository.updateReminder(updatedReminder)
                            scheduleNotification(context, updatedReminder)
                            Toast.makeText(context, "Reminder for '${updatedReminder.pillName}' updated!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        onBackPressed = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
private fun scheduleNotification(context: Context, reminder: Reminder) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
        putExtra("pillName", reminder.pillName)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.id.toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        val timeParts = reminder.time.split(":").map { it.toInt() }
        set(Calendar.HOUR_OF_DAY, timeParts[0])
        set(Calendar.MINUTE, timeParts[1])
        set(Calendar.SECOND, 0)
    }

    if (calendar.timeInMillis <= System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            // Fallback for when exact alarms are not allowed.
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    } else {
        // For older versions, setExact is fine without the special permission.
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}

private fun cancelNotification(context: Context, reminder: Reminder) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderBroadcastReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.id.toInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
}
