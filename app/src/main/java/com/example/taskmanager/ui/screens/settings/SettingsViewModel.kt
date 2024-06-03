package com.example.taskmanager.ui.screens.settings

import com.example.taskmanager.model.service.AccountService
import com.example.taskmanager.model.service.LogService
import com.example.taskmanager.model.service.StorageService
import com.example.taskmanager.ui.screens.TaskViewModel
import com.example.taskmanager.ui.screens.login.LoginDestination
import com.example.taskmanager.ui.screens.sign_up.SignUpDestination
import com.example.taskmanager.ui.screens.splash.SplashDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel  @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
) : TaskViewModel(logService) {

    val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous) }

    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LoginDestination.route)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SignUpDestination.route)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SplashDestination.route)
        }
    }

    fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            restartApp(SplashDestination.route)
        }
    }
}