package com.example.taskmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.taskmanager.TaskAppState
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

@Composable
fun Navigation(
    appState: TaskAppState,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(SplashDestination.route) {
            SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
        }

        composable(SettingsDestination.route) {
            SettingsScreen(
                restartApp = { route -> appState.clearAndNavigate(route) },
                openScreen = { route -> appState.navigate(route) }
            )
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
            route = EditTaskDestination.routeWithArg,
            arguments = listOf(navArgument(EditTaskDestination.TASK_ID) {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            })
        ) {
            EditTasksScreen(popUpScreen = { appState.popUp() })
        }
    }
}