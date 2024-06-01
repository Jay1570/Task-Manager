package com.example.taskmanager.ui.screens.tasks

import androidx.compose.runtime.mutableStateOf
import com.example.taskmanager.EDIT_TASK_SCREEN
import com.example.taskmanager.SETTINGS_SCREEN
import com.example.taskmanager.model.Task
import com.example.taskmanager.model.service.ConfigurationService
import com.example.taskmanager.model.service.LogService
import com.example.taskmanager.model.service.StorageService
import com.example.taskmanager.ui.screens.TaskViewModel
import com.example.taskmanager.ui.screens.edit_tasks.EditTaskDestination
import com.example.taskmanager.ui.screens.settings.SettingsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : TaskViewModel(logService) {

    val options = mutableStateOf<List<String>>(listOf())

    val tasks = emptyFlow<List<Task>>()

    fun loadTaskOptions() {
        //TODO
    }

    fun onTaskCheckChange(task: Task) {
        launchCatching { storageService.update(task.copy(completed = !task.completed)) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EditTaskDestination.route)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SettingsDestination.route)

    private fun onDeleteTaskClick(task: Task) {
        launchCatching { storageService.delete(task.id) }
    }

    private fun onFlagTaskCLick(task: Task) {
        launchCatching { storageService.update(task.copy(flag = !task.flag)) }
    }

    fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> openScreen(EditTaskDestination.routeWithArgs)
            TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
            TaskActionOption.ToggleFlag -> onFlagTaskCLick(task)
        }
    }
}