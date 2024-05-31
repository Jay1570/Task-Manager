package com.example.taskmanager.Model

enum class Priority {
    None,
    Low,
    Medium,
    High;

    companion object {
        fun getByteName(name: String?): Priority {
            values().forEach { priority -> if (name == priority.name) return priority }
            return None
        }
        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            values().forEach { priority -> options.add(priority.name) }
            return options
        }
    }
}