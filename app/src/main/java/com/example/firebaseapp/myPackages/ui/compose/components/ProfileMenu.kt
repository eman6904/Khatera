package com.example.firebaseapp.myPackages.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firebaseapp.R

@Composable
fun ProfileMenu(
    expanded: Boolean,
    profileImage: String?,
    onDismiss: () -> Unit,
    onUpdateClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .background(Color(0xFF212121))
            .clip(RoundedCornerShape(16.dp))
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.update_image),
                    color = Color.White
                )
            },
            onClick = onUpdateClick
        )

        if (profileImage != null) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.remove_image),
                        color = Color.White
                    )
                },
                onClick = onRemoveClick
            )
        }
    }
}
