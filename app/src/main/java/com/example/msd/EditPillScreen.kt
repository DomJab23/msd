package com.example.msd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditPillScreen(pill: Pill, onPillUpdated: (Pill) -> Unit, onBackPressed: () -> Unit) {
    var pillName by remember { mutableStateOf(pill.name) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Date: ${pill.date}")
        Text("Time: ${pill.time}")
        OutlinedTextField(
            value = pillName,
            onValueChange = { pillName = it },
            label = { Text("Pill Name") },
            modifier = Modifier.padding(top = 16.dp)
        )
        Button(
            onClick = { onPillUpdated(pill.copy(name = pillName)) },
            modifier = Modifier.padding(top = 16.dp),
            enabled = pillName.isNotBlank()
        ) {
            Text("Save")
        }
        Button(onClick = onBackPressed, modifier = Modifier.padding(top = 8.dp)) {
            Text("Back")
        }
    }
}