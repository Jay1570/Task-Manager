package com.example.taskmanager.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import com.example.taskmanager.common.composable.BasicToolBar
import com.example.taskmanager.common.composable.DangerousCardEditor
import com.example.taskmanager.common.composable.DialogCancelButton
import com.example.taskmanager.common.composable.DialogConfirmButton
import com.example.taskmanager.common.composable.RegularCardEditor
import com.example.taskmanager.common.ext.card
import com.example.taskmanager.common.ext.spacer
import com.example.taskmanager.ui.navigation.NavigationDestination
import com.example.taskmanager.ui.theme.TaskManagerTheme

object SettingsDestination : NavigationDestination {
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings
}

@Composable
fun SettingsScreen(
    restartApp: (String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState (initial = SettingsUiState(false))
    SettingsScreenContent(
        uiState = uiState,
        onLoginClick = { viewModel.onLoginClick(openScreen) },
        onSignUpClick = { viewModel.onSignUpClick(openScreen) },
        onSignOutClick = { viewModel.onSignOutClick(restartApp) },
        onDeleteMyAccountClick = { viewModel.onDeleteMyAccountClick(restartApp) }
    )
}

@Composable
fun SettingsScreenContent(
    uiState: SettingsUiState,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteMyAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolBar(title = SettingsDestination.titleRes)

        Spacer(modifier = Modifier.spacer())

        if (uiState.isAnonymousAccount) {
            RegularCardEditor(
                title = R.string.sign_in,
                icon = R.drawable.ic_sign_in,
                content = "",
                modifier = Modifier.card(),
                onEditClick = onLoginClick
            )

            RegularCardEditor(
                title = R.string.create_account,
                icon = R.drawable.ic_create_account,
                content = "",
                modifier = Modifier.card(),
                onEditClick = onSignUpClick
            )
        } else {
            SignOutCard(onSignOutClick)

            DeleteMyAccountCard(onDeleteMyAccountClick)
        }

    }
}

@Composable
fun SignOutCard(signOut: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor(
        title = R.string.sign_out,
        icon = R.drawable.ic_exit,
        content = "",
        modifier = Modifier.card(),
        onEditClick = { showWarningDialog = true }
    )

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(text = stringResource(id = R.string.sign_out_title)) },
            text = { Text(text = stringResource(id = R.string.sign_out_description)) },
            dismissButton = { DialogCancelButton(text = R.string.cancel, action = { showWarningDialog = false }) },
            onDismissRequest = { showWarningDialog = false },
            confirmButton = {
                DialogConfirmButton(
                    text = R.string.sign_out,
                    action = {
                        signOut()
                        showWarningDialog = false
                    }
                )
            }
        )
    }
}

@Composable
fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }

    DangerousCardEditor(
        title = R.string.delete_my_account,
        icon = R.drawable.ic_delete_account,
        content = "",
        modifier = Modifier.card(),
        onEditClick = { showWarningDialog = true }
    )

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(text = stringResource(id = R.string.delete_account_title)) },
            text = { Text(text = stringResource(id = R.string.delete_account_description)) },
            dismissButton = { DialogCancelButton(text = R.string.cancel, action = { showWarningDialog = false }) },
            onDismissRequest = { showWarningDialog = false },
            confirmButton = {
                DialogConfirmButton(
                    text = R.string.delete_my_account,
                    action = {
                        deleteMyAccount()
                        showWarningDialog = false
                    }
                )
            }
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    val uiState = SettingsUiState(isAnonymousAccount = false)

    TaskManagerTheme {
        SettingsScreenContent(
            uiState = uiState,
            onLoginClick = { },
            onSignUpClick = { },
            onSignOutClick = { },
            onDeleteMyAccountClick = { }
        )
    }
}