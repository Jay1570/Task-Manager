package com.example.taskmanager.ui.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.example.taskmanager.model.service.AccountService
import com.example.taskmanager.model.service.ConfigurationService
import com.example.taskmanager.model.service.LogService
import com.example.taskmanager.ui.screens.TaskViewModel
import com.example.taskmanager.ui.screens.tasks.TasksDestination
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
): TaskViewModel(logService){

    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        showError.value = false
        if (accountService.hasUser) openAndPopUp(TasksDestination.route, SplashDestination.route)
        else createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(TasksDestination.route, SplashDestination.route)
        }
    }
}