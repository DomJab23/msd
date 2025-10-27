package com.example.msd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.msd.ui.theme.MsdTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReminderScreen(
    reminder: Reminder,
    onReminderUpdated: (Reminder) -> Unit,
    onBackPressed: () -> Unit
) {
    var pillName by remember { mutableStateOf(reminder.pillName) }
    val timeParts = reminder.time.split(":").map { it.toInt() }
    val timePickerState = rememberTimePickerState(initialHour = timeParts[0], initialMinute = timeParts[1])

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = pillName,
            onValueChange = { pillName = it },
            label = { Text("Pill Name") }
        )
        TimePicker(state = timePickerState)
        Button(
            onClick = {
                val updatedTime = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                onReminderUpdated(reminder.copy(pillName = pillName, time = updatedTime))
            },
            modifier = Modifier.padding(top = 16.dp),
            enabled = pillName.isNotBlank()
        ) {
            Text("Save Changes")
        }
        Button(onClick = onBackPressed, modifier = Modifier.padding(top = 8.dp)) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditReminderScreenPreview() {
    MsdTheme {
        val sampleReminder = Reminder(1, "Aspirin", "10:00")
        EditReminderScreen(
            reminder = sampleReminder,
            onReminderUpdated = {},
            onBackPressed = {}
        )
    }
}
