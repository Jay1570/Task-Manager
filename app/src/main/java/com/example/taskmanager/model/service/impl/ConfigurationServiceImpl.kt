package com.example.taskmanager.model.service.impl

import com.example.taskmanager.R
import com.example.taskmanager.model.service.ConfigurationService
import com.example.taskmanager.model.service.trace
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {

    private val remoteConfig
        get() = Firebase.remoteConfig

    init {
        if (BuildConfig.DEBUG) {
            val configSetting = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
            remoteConfig.setConfigSettingsAsync(configSetting)
        }
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    override suspend fun fetchConfiguration(): Boolean =
        trace(FETCH_CONFIG_TRACE) {
            remoteConfig.fetchAndActivate().await()
        }

    override val isShowTaskEditButtonConfig: Boolean
        get() = remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()

    companion object {
        private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}