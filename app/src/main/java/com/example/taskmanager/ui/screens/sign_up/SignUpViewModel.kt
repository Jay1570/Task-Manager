package com.example.taskmanager.ui.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.example.taskmanager.R
import com.example.taskmanager.common.ext.isValidEmail
import com.example.taskmanager.common.ext.isValidPassword
import com.example.taskmanager.common.ext.passwordMatches
import com.example.taskmanager.common.snackbar.SnackbarManager
import com.example.taskmanager.model.service.AccountService
import com.example.taskmanager.model.service.LogService
import com.example.taskmanager.ui.screens.TaskViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : TaskViewModel(logService) {

    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(R.string.password_match_error)
            return
        }

        launchCatching {
            //TODO
        }
    }
}