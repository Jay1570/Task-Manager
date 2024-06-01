package com.example.taskmanager.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskmanager.R
import com.example.taskmanager.common.composable.BasicButton
import com.example.taskmanager.common.ext.basicButton
import com.example.taskmanager.ui.theme.TaskManagerTheme
import kotlinx.coroutines.delay


private const val SPLASH_TIMEOUT = 1000L
@Composable
fun SplashScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    SplashScreenContent(
        onAppStart = { viewModel.onAppStart(openAndPopUp) },
        shouldShowError = viewModel.showError.value
    )
}

@Composable
fun SplashScreenContent(
   onAppStart: () -> Unit,
   shouldShowError: Boolean,
   modifier: Modifier = Modifier
) {
    Column(
       modifier = modifier
           .fillMaxWidth()
           .fillMaxHeight()
           .background(color = MaterialTheme.colorScheme.background)
           .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (shouldShowError) {
            Text(text = stringResource(id = R.string.generic_error))
            BasicButton(
                text = R.string.try_again,
                action = onAppStart,
                modifier = Modifier.basicButton()
            )
        } else {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
        }
    }
    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        onAppStart()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    TaskManagerTheme {
        SplashScreenContent(onAppStart = {  }, shouldShowError = true)
    }
}
