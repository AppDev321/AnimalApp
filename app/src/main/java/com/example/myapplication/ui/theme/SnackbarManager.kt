package com.example.myapplication.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


object SnackbarManager {
    val snackbarHostState = SnackbarHostState()
}


@Composable
fun SnackbarWidget() {
    val coroutineScope = rememberCoroutineScope()
    val showSnackBar: (
        message: String?,
        actionLabel: String,
        actionPerformed: () -> Unit,
        dismissed: () -> Unit
    ) -> Unit = { message, actionLabel, actionPerformed, dismissed ->
        coroutineScope.launch {
            val snackBarResult =SnackbarManager.snackbarHostState.showSnackbar(
                message = message.toString(),
                actionLabel = actionLabel
            )
            when (snackBarResult) {
                SnackbarResult.ActionPerformed -> actionPerformed.invoke()
                SnackbarResult.Dismissed -> dismissed.invoke()
            }
        }
    }



}