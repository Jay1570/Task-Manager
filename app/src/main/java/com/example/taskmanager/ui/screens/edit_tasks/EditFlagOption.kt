package com.example.taskmanager.ui.screens.edit_tasks

enum class EditFlagOption {
    On,
    Off;

    companion object {
        fun getByCheckedState(checkedState: Boolean?): EditFlagOption {
            val hasFlag = checkedState?: false
            return if (hasFlag) On else Off
        }

        fun getBooleanValue(flagOption: String): Boolean {
            return flagOption == On.name
        }

        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            entries.forEach { flagOption -> options.add(flagOption.name) }
            return options
        }
    }
}