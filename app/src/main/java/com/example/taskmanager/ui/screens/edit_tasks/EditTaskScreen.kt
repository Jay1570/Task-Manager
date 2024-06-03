package com.example.taskmanager.ui.screens.edit_tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import com.example.taskmanager.common.composable.ActionToolBar
import com.example.taskmanager.common.composable.BasicField
import com.example.taskmanager.common.composable.CardSelector
import com.example.taskmanager.common.composable.RegularCardEditor
import com.example.taskmanager.common.ext.card
import com.example.taskmanager.common.ext.fieldModifier
import com.example.taskmanager.common.ext.spacer
import com.example.taskmanager.common.ext.toolbarActions
import com.example.taskmanager.model.Priority
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.navigation.NavigationDestination
import com.example.taskmanager.ui.theme.TaskManagerTheme
import java.util.Calendar

object EditTaskDestination : NavigationDestination{
    override val route = "EditTaskScreen"
    override val titleRes: Int = R.string.edit_task
    val TASK_ID = "taskId"
    val routeWithArg = "$route?$TASK_ID={$TASK_ID}"
}
@Composable
fun EditTasksScreen(
    popUpScreen: () -> Unit,
    viewModel: EditTaskViewModel = hiltViewModel()
) {
    val task by viewModel.task
    
    EditTaskScreenContent(
        task = task,
        onDoneClick = { viewModel.onDoneClick(popUpScreen) },
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onUrlChange = viewModel::onUrlChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onPriorityChange = viewModel::onPriorityChange,
        onFlagToggle = viewModel::onFlagToggle,
    )
}

@Composable
fun EditTaskScreenContent(
    task: Task,
    onDoneClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUrlChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onTimeChange:(Int, Int) -> Unit,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionToolBar(
            title = EditTaskDestination.titleRes,
            endActionIcon = R.drawable.ic_check,
            endAction = onDoneClick,
            modifier = Modifier.toolbarActions()
        )
        
        Spacer(modifier = Modifier.spacer())

        val fieldModifier = Modifier.fieldModifier()
        
        BasicField(
            text = R.string.title,
            value = task.title,
            onNewValue = onTitleChange,
            modifier = fieldModifier
        )

        BasicField(
            text = R.string.description,
            value = task.description,
            onNewValue = onDescriptionChange,
            modifier = fieldModifier
        )
        
        BasicField(
            text = R.string.url,
            value = task.url,
            onNewValue = onUrlChange,
            modifier = fieldModifier
        )

        Spacer(modifier = Modifier.spacer())

        CardEditors(task = task, onDateChange = onDateChange, onTimeChange = onTimeChange)

        CardSelectors(task = task, onPriorityChange = onPriorityChange, onFlagToggle = onFlagToggle)

        Spacer(modifier = Modifier.spacer())
    }
}

@Composable
private fun CardEditors(
    task: Task,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
) {
    val context = LocalContext.current
    RegularCardEditor(
        title = R.string.date,
        icon = R.drawable.ic_calendar,
        content =  task.dueDate,
        modifier =  Modifier.card(),
        onEditClick = { showDatePicker(context, onDateChange) }
    )

    RegularCardEditor(
        title = R.string.time,
        icon = R.drawable.ic_clock,
        content = task.dueTime,
        modifier = Modifier.card(),
        onEditClick = { showTimePicker(context, onTimeChange) }
    )
}

@Composable
private fun CardSelectors(
    task: Task,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit
) {
    val prioritySelection = Priority.getByName(task.priority).name
    CardSelector(
        label = R.string.priority,
        options = Priority.getOptions(),
        selection = prioritySelection,
        modifier = Modifier.card(),
        onNewValue = { newValue -> onPriorityChange(newValue) }
    )

    val flagSelection = EditFlagOption.getByCheckedState(task.flag).name
    CardSelector(
        label =R.string.flag,
        options = EditFlagOption.getOptions(),
        selection = flagSelection,
        modifier = Modifier.card(),
        onNewValue = { newValue -> onFlagToggle(newValue) }
    )
}

private fun showDatePicker(context: Context, onDateChange: (Long) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePicker =
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                onDateChange(selectedDate.timeInMillis)
            },
            year,
            month,
            day
        )
    datePicker.show()
}

private fun showTimePicker(context: Context, onTimeChange: (Int, Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePicker =
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                onTimeChange(selectedHour, selectedMinute)
            },
            hour,
            minute,
            true
        )

    timePicker.show()
}

@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    val task = Task(
        title = "Task title",
        description = "Task description",
        flag = true
    )

    TaskManagerTheme {
        EditTaskScreenContent(
            task = task,
            onDoneClick = { },
            onTitleChange = { },
            onDescriptionChange = { },
            onUrlChange = { },
            onDateChange = { },
            onTimeChange = { _, _ -> },
            onPriorityChange = { },
            onFlagToggle = { }
        )
    }
}