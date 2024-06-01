@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskmanager

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskmanager.common.composable.PermissionDialog
import com.example.taskmanager.common.composable.RationaleDialog
import com.example.taskmanager.common.snackbar.SnackbarManager
import com.example.taskmanager.ui.screens.edit_tasks.EditTaskDestination
import com.example.taskmanager.ui.screens.edit_tasks.EditTasksScreen
import com.example.taskmanager.ui.screens.login.LoginDestination
import com.example.taskmanager.ui.screens.login.LoginScreen
import com.example.taskmanager.ui.screens.settings.SettingsDestination
import com.example.taskmanager.ui.screens.settings.SettingsScreen
import com.example.taskmanager.ui.screens.sign_up.SignUpDestination
import com.example.taskmanager.ui.screens.sign_up.SignUpScreen
import com.example.taskmanager.ui.screens.splash.SplashDestination
import com.example.taskmanager.ui.screens.splash.SplashScreen
import com.example.taskmanager.ui.screens.tasks.TasksDestination
import com.example.taskmanager.ui.screens.tasks.TasksScreen
import com.example.taskmanager.ui.theme.TaskManagerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope


@Composable
fun TaskApp() {
    TaskManagerTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }
        Surface(color = MaterialTheme.colorScheme.background){
            val appState = rememberAppState()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackbarState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData = snackbarData,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                    )
                },
            ) { innerPadding ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SplashDestination.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    taskGraph(appState)
                }
            }
        }
    }
}

fun NavGraphBuilder.taskGraph(appState: TaskAppState) {
    composable(SplashDestination.route) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SettingsDestination.route) {
        SettingsScreen()
    }

    composable(LoginDestination.route) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SignUpDestination.route) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(TasksDestination.route) {
        TasksScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable(
        route = EditTaskDestination.routeWithArgs,
        arguments = listOf(navArgument(EditTaskDestination.taskIdArg) {
            nullable = true
            defaultValue = null
        })
    ) {
        EditTasksScreen()
    }

    composable(EditTaskDestination.route) {
        EditTasksScreen()
    }
}

@Composable
fun rememberAppState(
    snackbarState: SnackbarHostState = remember {SnackbarHostState()},
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(snackbarState, navController, snackbarManager, resources, coroutineScope) {
        TaskAppState(snackbarState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}