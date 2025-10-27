package com.example.msd

object PillRepository {
    private val pills = mutableListOf<Pill>()
    private var nextId = 1L

    fun addPill(pill: Pill) {
        pills.add(pill.copy(id = nextId++))
    }

    fun getPills(): List<Pill> {
        return pills.sortedByDescending { it.date + it.time }
    }

    fun getPillById(id: Long): Pill? {
        return pills.find { it.id == id }
    }

    fun updatePill(pill: Pill) {
        val index = pills.indexOfFirst { it.id == pill.id }
        if (index != -1) {
            pills[index] = pill
        }
    }
}