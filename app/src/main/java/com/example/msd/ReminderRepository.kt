package com.example.msd

object ReminderRepository {
    private val reminders = mutableListOf<Reminder>()
    private var nextId = 1L

    fun addReminder(reminder: Reminder): Reminder {
        val newReminder = reminder.copy(id = nextId++)
        reminders.add(newReminder)
        return newReminder
    }

    fun getReminders(): List<Reminder> {
        return reminders.sortedBy { it.isDone }
    }

    fun getReminderById(id: Long): Reminder? {
        return reminders.find { it.id == id }
    }

    fun updateReminder(reminder: Reminder) {
        val index = reminders.indexOfFirst { it.id == reminder.id }
        if (index != -1) {
            reminders[index] = reminder
        }
    }

    fun deleteReminder(id: Long) {
        reminders.removeAll { it.id == id }
    }
}