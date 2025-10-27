package com.example.msd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
fun CalendarScreen(onBackPressed: () -> Unit, onSubmit: (Long, String) -> Unit) {
    val datePickerState = rememberDatePickerState()
    var pillName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DatePicker(state = datePickerState)

        if (datePickerState.selectedDateMillis != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = pillName,
                    onValueChange = { pillName = it },
                    label = { Text("Pill Name") },
                    modifier = Modifier.padding(top = 16.dp)
                )
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onSubmit(it, pillName)
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp),
                    enabled = pillName.isNotBlank()
                ) {
                    Text("Submit")
                }
            }
        }

        Button(onClick = onBackPressed, modifier = Modifier.padding(top = 8.dp)) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    MsdTheme {
        CalendarScreen(onBackPressed = {}, onSubmit = { _, _ -> })
    }
}
