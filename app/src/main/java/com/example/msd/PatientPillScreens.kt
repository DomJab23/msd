package com.example.msd

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items



@Composable
fun ViewPillListScreen(pills: List<Pill>, onPillClicked: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Logged Pills", modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(pills) { pill ->
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
                            Text("Pill: ${pill.name}")
                            Text("Date: ${pill.date}")
                            Text("Time: ${pill.time}")
                        }
                    }
                }
            }
        }
    }
}
