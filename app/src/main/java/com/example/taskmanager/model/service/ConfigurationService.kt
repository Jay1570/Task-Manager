package com.example.taskmanager.model.service

interface ConfigurationService {
    val isShowTaskEditButtonConfig: Boolean
    suspend fun fetchConfiguration(): Boolean
}