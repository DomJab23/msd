package com.example.msd

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(    mainNavController: NavController,
                   darkTheme: Boolean,
                   onThemeChange: (Boolean) -> Unit,
                   fontSize: Float,
                   onFontSizeChange: (Float) -> Unit
) {
    val navController = rememberNavController()
    var refreshTrigger by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { navController.navigate("dashboard") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.DateRange, contentDescription = "Dashboard")
                }
                IconButton(onClick = { navController.navigate("reminders") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Notifications, contentDescription = "Reminders")
                }
                IconButton(onClick = { navController.navigate("profile") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Person, contentDescription = "Profile")
                }
                IconButton(onClick = { navController.navigate("settings") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { mainNavController.navigate("calendar") }) {
                Icon(Icons.Default.Add, contentDescription = "Log a New Pill")
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(it)
        ) {
            composable("dashboard") {
                PillLoggingScreen(
                    pills = PillRepository.getPills(),
                    onEditPillClicked = { pillId ->
                        mainNavController.navigate("edit_pill/$pillId")
                    }
                )
            }
            composable("reminders") {
                RemindersScreen(
                    reminders = ReminderRepository.getReminders(),
                    onAddReminderClicked = { mainNavController.navigate("add_reminder") },
                    onEditReminderClicked = { reminderId ->
                        mainNavController.navigate("edit_reminder/$reminderId")
                    },
                    onDeleteReminderClicked = { reminderId ->
                        val reminder = ReminderRepository.getReminderById(reminderId)
                        if (reminder != null) {
                            cancelNotification(context, reminder)
                        }
                        ReminderRepository.deleteReminder(reminderId)
                        refreshTrigger++
                    },
                    onDoneClicked = { reminder ->
                        cancelNotification(context, reminder)
                        ReminderRepository.updateReminder(reminder.copy(isDone = true))
                        refreshTrigger++
                    }
                )
            }
            composable("profile") {
                ProfileScreen(onLogoffClicked = { mainNavController.popBackStack() })
            }
            composable("settings") {
                SettingsScreen(darkTheme = darkTheme,
                    onThemeChange = onThemeChange,
                    fontSize = fontSize,
                    onFontSizeChange = onFontSizeChange)
            }
        }
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
