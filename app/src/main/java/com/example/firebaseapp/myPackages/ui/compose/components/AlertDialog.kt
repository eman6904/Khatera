package com.example.firebaseapp.myPackages.ui.compose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.firebaseapp.R

@Composable
fun AlertDialog(
    title:String,
    body:String,
    confirmText:String,
    onConfirmClick:()->Unit,
    onCancelClick:()->Unit
){
    AlertDialog(
        onDismissRequest = {
            onCancelClick()
        },
        title = { Text(title) },
        text = { Text(body) },
        confirmButton = {
            TextButton(onClick = {
                onConfirmClick()
            }) {
                Text(
                    text = confirmText,
                    color = Color(0xFF558B2F)
                    )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onCancelClick()
            }) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = Color(0xFF558B2F)
                )
            }
        }
    )
}