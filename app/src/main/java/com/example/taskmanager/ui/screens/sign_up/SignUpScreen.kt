package com.example.taskmanager.ui.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import com.example.taskmanager.common.composable.BasicButton
import com.example.taskmanager.common.composable.BasicToolBar
import com.example.taskmanager.common.composable.EmailField
import com.example.taskmanager.common.composable.PasswordField
import com.example.taskmanager.common.composable.RepeatPasswordField
import com.example.taskmanager.common.ext.basicButton
import com.example.taskmanager.common.ext.fieldModifier
import com.example.taskmanager.ui.navigation.NavigationDestination
import com.example.taskmanager.ui.theme.TaskManagerTheme


object SignUpDestination : NavigationDestination {
    override val route: String = "login"
    override val titleRes: Int = R.string.create_account
}

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) }
    )
}

@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val fieldModifier = Modifier.fieldModifier()

    BasicToolBar(title = SignUpDestination.titleRes)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(value = uiState.email, onNewValue = onEmailChange, modifier = fieldModifier)
        PasswordField(value = uiState.password, onNewValue = onPasswordChange, modifier = fieldModifier)
        RepeatPasswordField(value = uiState.repeatPassword, onNewValue = onRepeatPasswordChange, modifier = fieldModifier)
        BasicButton(text = R.string.create_account, action = { onSignUpClick() }, modifier = Modifier.basicButton())
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )

    TaskManagerTheme {
        SignUpScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onRepeatPasswordChange = { },
            onSignUpClick = { }
        )
    }
}


