package com.example.taskmanager

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.common.composable.PermissionDialog
import com.example.taskmanager.common.composable.RationaleDialog
import com.example.taskmanager.common.snackbar.SnackbarManager
import com.example.taskmanager.ui.navigation.Navigation
import com.example.taskmanager.ui.screens.splash.SplashDestination
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
        Surface(color = MaterialTheme.colorScheme.background) {
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
                Navigation(
                    appState = appState,
                    startDestination = SplashDestination.route,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun rememberAppState(
    snackbarState: SnackbarHostState = remember { SnackbarHostState() },
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
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}