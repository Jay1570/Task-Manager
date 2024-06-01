package com.example.taskmanager.ui.screens.settings

import androidx.compose.runtime.Composable
import com.example.taskmanager.R
import com.example.taskmanager.ui.navigation.NavigationDestination

object SettingsDestination : NavigationDestination {
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings
}

@Composable
fun SettingsScreen() {
    //TODO
}