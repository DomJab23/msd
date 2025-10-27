package com.example.msd

data class Reminder(val id: Long, val pillName: String, val time: String, val isDone: Boolean = false)