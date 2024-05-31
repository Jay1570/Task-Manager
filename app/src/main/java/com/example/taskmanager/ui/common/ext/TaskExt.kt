package com.example.taskmanager.ui.common.ext

import com.example.taskmanager.Model.Task

fun Task?.hasDueDate(): Boolean {
    return this?.dueDate.orEmpty().isNotBlank()
}

fun Task?.hasDueTIme(): Boolean {
    return this?.dueTime.orEmpty().isNotBlank()
}