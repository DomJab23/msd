package com.example.msd

import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientHomeScreen(takeThePill : () -> Unit, pills: List<Pill>) {
    var expanded by remember { mutableStateOf(false) }

    var selectedText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.Center) {
        Text(text = "Home Screen",
            fontSize = 24.sp
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Choose the pill")
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                label = { Text("Label") },
                readOnly = true, // Important so typing doesn’t open keyboard
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor() // 🔑 This aligns the dropdown properly
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                pills.forEach { pill ->
                    DropdownMenuItem(
                        text = { Text(pill.name) },
                        onClick = {
                            selectedText = pill.name
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        FilledTonalButton (
            onClick = { takeThePill },
            modifier = Modifier
                .clip(CircleShape)
                .size(300.dp)
        ) {
            Text("Take the pill")
        }
    }
}


@Preview
@Composable
fun PatientHomeScreenPreview() {
    val samplePills = listOf(
        Pill(1, "Aspirin", "2024-01-01", "10:00"),
        Pill(2, "Ibuprofen", "2024-01-02", "12:30")
    )
    PatientHomeScreen ({}, pills = samplePills)
}
