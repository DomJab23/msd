package com.example.msd

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pillName = intent.getStringExtra("pillName")

        val builder = NotificationCompat.Builder(context, "pill_reminders")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Pill Reminder")
            .setContentText("It's time to take your $pillName.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }
}