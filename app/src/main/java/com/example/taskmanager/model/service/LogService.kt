package com.example.taskmanager.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}