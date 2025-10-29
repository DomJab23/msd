package com.example.msd

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.msd.ui.theme.MsdTheme

@Composable
fun SettingsScreen(
    darkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    fontSize: Float,
    onFontSizeChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings Screen", fontSize = 24.sp)

        // Theme selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark Theme")
            Switch(
                checked = darkTheme,
                onCheckedChange = onThemeChange // Use the callback
            )
        }

        // Font size selection
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Font Size: ${fontSize.toInt()}sp")
            Slider(
                value = fontSize,
                onValueChange = onFontSizeChange, // Use the callback
                valueRange = 12f..24f,
                steps = 5
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    // Pass default values for the preview
    MsdTheme {
        SettingsScreen(
            darkTheme = false,
            onThemeChange = {},
            fontSize = 16f,
            onFontSizeChange = {}
        )
    }
}
