package com.example.msd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.msd.ui.theme.MsdTheme

@Composable
fun PillLoggingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome!")
        Button(onClick = { /* TODO: Handle pill logging */ }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Log Pill")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PillLoggingScreenPreview() {
    MsdTheme {
        PillLoggingScreen()
    }
}