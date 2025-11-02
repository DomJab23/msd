package com.example.msd

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
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
fun PatientScreen(
    mainNavController: NavController,
    darkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    fontSize: Float,
    onFontSizeChange: (Float) -> Unit
) {
    val navController = rememberNavController()
    var refreshTrigger by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { navController.navigate("pills") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pills")
                }
                IconButton(onClick = { navController.navigate("reminders") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Notifications, contentDescription = "Reminders")
                }
                IconButton(onClick = { navController.navigate("settings") }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "reminders",
            modifier = Modifier.padding(it)
        ) {
            composable("reminders") {
                RemindersScreen(
                    reminders = ReminderRepository.getReminders() ?: emptyList(),
                    onAddReminderClicked = { mainNavController.navigate("add_reminder") },
                    onEditReminderClicked = { reminderId ->
                        mainNavController.navigate("edit_reminder/$reminderId")
                    },
                    onDeleteReminderClicked = { reminderId ->
                        val reminder = ReminderRepository.getReminderById(reminderId)
                        if (reminder != null) {
                            ReminderRepository.deleteReminder(reminderId)
                        }
                        refreshTrigger++
                    },
                    onDoneClicked = { reminder ->
                        ReminderRepository.updateReminder(reminder.copy(isDone = true))
                        refreshTrigger++
                    }
                )
            }
            composable("pills") {
                PillLoggingScreen(
                    pills = PillRepository.getPills() ?: emptyList(),
                    onEditPillClicked = { pillId ->
                        mainNavController.navigate("edit_pill/$pillId")
                    }
                )
            }
            composable("settings") {
                SettingsScreen(darkTheme = darkTheme,
                    onThemeChange = onThemeChange,
                    fontSize = fontSize,
                    onFontSizeChange = onFontSizeChange,
                    onLogoffClicked = {
                        mainNavController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        } })
            }
        }
    }
}
