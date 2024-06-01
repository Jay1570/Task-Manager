package com.example.taskmanager.ui.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.R
import com.example.taskmanager.common.composable.DropdownContextMenu
import com.example.taskmanager.common.ext.contextMenu
import com.example.taskmanager.common.ext.hasDueDate
import com.example.taskmanager.common.ext.hasDueTime
import com.example.taskmanager.model.Task

@Composable
fun TaskItem(
    task: Task,
    options: List<String>,
    onCheckChange: () -> Unit,
    onActionClick: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.padding(8.dp,0.dp,8.dp,8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { onCheckChange() },
                modifier = Modifier.padding(8.dp,0.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = getDueDateAndTime(task),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            if (task.flag) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_flag),
                    contentDescription = "Flag",
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            }
            DropdownContextMenu(options = options, modifier = Modifier.contextMenu(), onActionClick = onActionClick)
        }
    }
}

private fun getDueDateAndTime(task: Task): String {
    val stringBuilder = StringBuilder("")

    if (task.hasDueDate()) {
        stringBuilder.append(task.dueDate)
        stringBuilder.append(" ")
    }
    if (task.hasDueTime()) {
        stringBuilder.append("at ")
        stringBuilder.append(task.dueTime)
    }
    return stringBuilder.toString()
}