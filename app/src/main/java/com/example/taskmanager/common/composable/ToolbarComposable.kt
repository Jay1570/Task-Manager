@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskmanager.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun BasicToolBar(
    @StringRes title: Int
) {
    TopAppBar(title = { Text(stringResource(title)) }, colors = toolbarColor())
}

@Composable
fun ActionToolBar(
   @StringRes title: Int,
   @DrawableRes endActionIcon: Int,
   modifier: Modifier,
   endAction: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = title)) },
        colors = toolbarColor(),
        actions = {
            Box(modifier) {
                IconButton(onClick = endAction) {
                    Icon(painter = painterResource(id = endActionIcon), contentDescription = "Action")
                }
            }
        }
    )
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): TopAppBarColors {
    return if (darkTheme) TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.tertiary)
    else TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondary)
}