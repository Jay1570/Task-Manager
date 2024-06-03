package com.example.taskmanager.ui.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskmanager.R
import com.example.taskmanager.common.composable.ActionToolBar
import com.example.taskmanager.common.ext.smallSpacer
import com.example.taskmanager.common.ext.toolbarActions
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.navigation.NavigationDestination
import com.example.taskmanager.ui.theme.TaskManagerTheme

object TasksDestination : NavigationDestination {
    override val route: String = "tasks"
    override val titleRes: Int = R.string.tasks
}

@Composable
fun TasksScreen(
    openScreen: (String) -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val tasks = viewModel.tasks.collectAsStateWithLifecycle(emptyList(), lifecycleOwner.lifecycle)
    val options:List<String> by viewModel.options

    TasksScreenContent(
        tasks = tasks.value,
        options = options,
        onAddClick = viewModel::onAddClick,
        onSettingsClick = viewModel::onSettingsClick,
        onTaskCheckChange = viewModel::onTaskCheckChange,
        onTaskActionClick = viewModel::onTaskActionClick,
        openScreen = openScreen
    )

    LaunchedEffect(Unit) { viewModel.loadTaskOptions() }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TasksScreenContent(
    tasks: List<Task>,
    options: List<String>,
    onAddClick: ((String) -> Unit) -> Unit,
    onSettingsClick: ((String) -> Unit) -> Unit,
    onTaskCheckChange:(Task) -> Unit,
    onTaskActionClick: ((String) -> Unit, Task, String) -> Unit,
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton =  {
            FloatingActionButton(
                onClick = { onAddClick(openScreen) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ActionToolBar(
                title = TasksDestination.titleRes,
                endActionIcon = R.drawable.ic_settings,
                endAction = { onSettingsClick(openScreen) },
                modifier = Modifier.toolbarActions(),
            )

            Spacer(modifier = Modifier.smallSpacer())
            LazyColumn {
                items(tasks, key = { it.id }) {taskItem ->
                    TaskItem(
                        task = taskItem,
                        options = options,
                        onCheckChange = { onTaskCheckChange(taskItem) },
                        onActionClick = { action -> onTaskActionClick(openScreen, taskItem, action) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    val task = Task(
        title = "Task title",
        flag = true,
        completed = true
    )

    val options = TaskActionOption.getOptions(hasEditOption = true)
    TaskManagerTheme {
        TasksScreenContent(
            tasks = listOf(task),
            options = options,
            onAddClick = { },
            onSettingsClick = { },
            onTaskCheckChange = { },
            onTaskActionClick = { _, _, _ -> },
            openScreen = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenDarkPreview() {
    val task = Task(
        title = "Task title",
        flag = true,
        completed = true
    )

    val options = TaskActionOption.getOptions(hasEditOption = true)
    TaskManagerTheme(darkTheme = true) {
        TasksScreenContent(
            tasks = listOf(task),
            options = options,
            onAddClick = { },
            onSettingsClick = { },
            onTaskCheckChange = { },
            onTaskActionClick = { _, _, _ -> },
            openScreen = { }
        )
    }
}