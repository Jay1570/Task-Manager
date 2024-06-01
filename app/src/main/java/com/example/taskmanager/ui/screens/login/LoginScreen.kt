package com.example.taskmanager.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import com.example.taskmanager.common.composable.BasicButton
import com.example.taskmanager.common.composable.BasicTextButton
import com.example.taskmanager.common.composable.BasicToolBar
import com.example.taskmanager.common.composable.EmailField
import com.example.taskmanager.common.composable.PasswordField
import com.example.taskmanager.common.ext.basicButton
import com.example.taskmanager.common.ext.fieldModifier
import com.example.taskmanager.common.ext.textButton
import com.example.taskmanager.ui.theme.TaskManagerTheme

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
        onForgotPasswordClick = viewModel::onForgotPasswordClick
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicToolBar(title = R.string.login_details)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            value = uiState.email,
            onNewValue = onEmailChange,
            modifier = Modifier.fieldModifier()
        )
        PasswordField(
            value = uiState.password,
            onNewValue = onPasswordChange,
            modifier = Modifier.fieldModifier()
        )
        BasicButton(
            text = R.string.sign_in,
            action = { onSignInClick() },
            modifier = Modifier.basicButton()
        )
        BasicTextButton(
            text = R.string.forgot_password,
            action = onForgotPasswordClick,
            modifier = Modifier.textButton()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val uiState = LoginUiState(
        email = "email@test.com"
    )
    TaskManagerTheme {
        LoginScreenContent(
            uiState = uiState,
            onEmailChange = {},
            onPasswordChange = {},
            onSignInClick = {  },
            onForgotPasswordClick = {  })
    }
}