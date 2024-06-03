package com.example.taskmanager.ui.screens.tasks

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.model.Task
import com.example.taskmanager.model.service.ConfigurationService
import com.example.taskmanager.model.service.LogService
import com.example.taskmanager.model.service.StorageService
import com.example.taskmanager.ui.screens.TaskViewModel
import com.example.taskmanager.ui.screens.edit_tasks.EditTaskDestination
import com.example.taskmanager.ui.screens.settings.SettingsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : TaskViewModel(logService) {


    val tasks = storageService.tasks
    val options = mutableStateOf<List<String>>(listOf())

    init {
        viewModelScope.launch {
            tasks.collect { fetchedTasks ->
                Log.d("TasksViewModel", "Loaded tasks: ${fetchedTasks.size}")
            }
        }
    }

    fun loadTaskOptions() {
        val hasEditOption = configurationService.isShowTaskEditButtonConfig
        options.value = TaskActionOption.getOptions(hasEditOption)
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
            TaskActionOption.EditTask -> openScreen("${EditTaskDestination.route}${EditTaskDestination.taskIdArgs}")
            TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
            TaskActionOption.ToggleFlag -> onFlagTaskCLick(task)
        }
    }
}