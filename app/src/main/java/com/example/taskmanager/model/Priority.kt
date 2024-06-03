package com.example.taskmanager.model

enum class Priority {
    None,
    Low,
    Medium,
    High;

    companion object {
        fun getByName(name: String?): Priority {
            entries.forEach { priority -> if (name == priority.name) return priority }
            return None
        }
        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            entries.forEach { priority -> options.add(priority.name) }
            return options
        }
    }
}