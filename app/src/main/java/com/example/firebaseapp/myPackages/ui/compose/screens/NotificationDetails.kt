package com.example.firebaseapp.myPackages.ui.compose.screens

import android.icu.text.CaseMap
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontStyle.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.models.NoteContent
import com.example.firebaseapp.myPackages.ui.compose.components.ProfileBottomSheet
import com.example.firebaseapp.myPackages.ui.compose.components.ProfileImage

@Composable
fun NotificationDetails(
    onBack: () -> Unit,
    clickedNote: NoteContent?
) {

    var note by remember { mutableStateOf<NoteContent?>(null) }
    var showProfile by remember { mutableStateOf(false) }
    if (showProfile && note != null) {
        ProfileBottomSheet(
            user = note!!.user!!,
            onDismiss = {
                showProfile = false
            },
            onItemClick = {
                note = it
                showProfile = false
            }
        )
    }
    LaunchedEffect(Unit) {
        note = clickedNote
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    showProfile = true
                }
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 40.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            ProfileImage(
                imageUrl = note?.user?.profileImage,
                size = 90
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp, end = 4.dp)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.created_by),
                    fontSize = 16.sp,
                    color = Color(0xFF558B2F),
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Text(
                    text = note?.user?.userName ?: "",
                    fontSize = 14.sp,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Text(
                    text = stringResource(R.string.created_at),
                    fontSize = 16.sp,
                    color = Color(0xFF558B2F),
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Text(
                    text = note?.date ?: "",
                    fontSize = 14.sp,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Text(
                    text = stringResource(R.string.title2),
                    fontSize = 16.sp,
                    color = Color(0xFF558B2F)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Text(
                    text = note?.title ?: "",
                    fontSize = 14.sp,
                    fontStyle = Normal,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Text(
                    text = stringResource(R.string.content),
                    fontSize = 16.sp,
                    color = Color(0xFF558B2F)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Text(
                    text = note?.note ?: "",
                    fontSize = 14.sp,
                    fontStyle = Normal,
                    color = Color.White
                )

            }
        }

    }
}