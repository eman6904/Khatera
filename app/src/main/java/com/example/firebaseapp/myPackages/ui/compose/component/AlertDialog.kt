package com.example.firebaseapp.myPackages.ui.compose.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

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
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onCancelClick()
            }) {
                Text("Cancel")
            }
        }
    )
}