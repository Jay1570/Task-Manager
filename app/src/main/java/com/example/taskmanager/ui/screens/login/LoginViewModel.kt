package com.example.taskmanager.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import com.example.taskmanager.LOGIN_SCREEN
import com.example.taskmanager.R
import com.example.taskmanager.SETTINGS_SCREEN
import com.example.taskmanager.common.ext.isValidEmail
import com.example.taskmanager.common.snackbar.SnackbarManager
import com.example.taskmanager.model.service.AccountService
import com.example.taskmanager.model.service.LogService
import com.example.taskmanager.ui.screens.TaskViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : TaskViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp : (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }
        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(SETTINGS_SCREEN, LOGIN_SCREEN)
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(R.string.recovery_email_sent)
        }
    }
}