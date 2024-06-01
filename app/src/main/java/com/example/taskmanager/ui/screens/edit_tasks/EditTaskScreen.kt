package com.example.taskmanager.ui.screens.edit_tasks

import androidx.compose.runtime.Composable
import com.example.taskmanager.R
import com.example.taskmanager.ui.navigation.NavigationDestination

object EditTaskDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.edit_task
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

@Composable
fun EditTasksScreen() {
    //TODO
}