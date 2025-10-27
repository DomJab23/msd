package com.example.msd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.msd.ui.theme.MsdTheme

@Composable
fun RemindersScreen(
    reminders: List<Reminder>,
    onAddReminderClicked: () -> Unit,
    onEditReminderClicked: (Long) -> Unit,
    onDeleteReminderClicked: (Long) -> Unit,
    onDoneClicked: (Reminder) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your Reminders", modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(reminders) { reminder ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Pill: ${reminder.pillName}",
                                textDecoration = if (reminder.isDone) TextDecoration.LineThrough else null,
                                color = if (reminder.isDone) Color.Gray else Color.Unspecified
                            )
                            Text(
                                text = "Time: ${reminder.time}",
                                textDecoration = if (reminder.isDone) TextDecoration.LineThrough else null,
                                color = if (reminder.isDone) Color.Gray else Color.Unspecified
                            )
                        }
                        IconButton(onClick = { onDoneClicked(reminder) }, enabled = !reminder.isDone) {
                            Icon(Icons.Default.Done, contentDescription = "Mark as Done")
                        }
                        IconButton(onClick = { onEditReminderClicked(reminder.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { onDeleteReminderClicked(reminder.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
        Button(onClick = onAddReminderClicked, modifier = Modifier.padding(top = 16.dp)) {
            Text("Add a New Reminder")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RemindersScreenPreview() {
    MsdTheme {
        val sampleReminders = listOf(
            Reminder(1, "Aspirin", "10:00"),
            Reminder(2, "Ibuprofen", "18:30", isDone = true)
        )
        RemindersScreen(
            reminders = sampleReminders,
            onAddReminderClicked = {},
            onEditReminderClicked = {},
            onDeleteReminderClicked = {},
            onDoneClicked = {}
        )
    }
}
