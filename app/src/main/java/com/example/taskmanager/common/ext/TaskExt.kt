package com.example.taskmanager.common.ext

import com.example.taskmanager.model.Task

fun Task?.hasDueDate(): Boolean {
    return this?.dueDate.orEmpty().isNotBlank()
}

fun Task?.hasDueTIme(): Boolean {
    return this?.dueTime.orEmpty().isNotBlank()
}