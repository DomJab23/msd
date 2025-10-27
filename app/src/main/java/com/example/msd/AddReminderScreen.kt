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
fun AddReminderScreen(onAddReminder: (String, Int, Int) -> Unit, onBackPressed: () -> Unit) {
    var pillName by remember { mutableStateOf("") }
    val timePickerState = rememberTimePickerState()

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
            onClick = { onAddReminder(pillName, timePickerState.hour, timePickerState.minute) },
            modifier = Modifier.padding(top = 16.dp),
            enabled = pillName.isNotBlank()
        ) {
            Text("Save Reminder")
        }
        Button(onClick = onBackPressed, modifier = Modifier.padding(top = 8.dp)) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddReminderScreenPreview() {
    MsdTheme {
        AddReminderScreen(onAddReminder = { _, _, _ -> }, onBackPressed = {})
    }
}