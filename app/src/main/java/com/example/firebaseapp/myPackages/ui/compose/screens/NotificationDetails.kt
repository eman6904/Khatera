package com.example.firebaseapp.myPackages.ui.compose.screens

import android.util.Log
import androidx.compose.foundation.background
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
import com.example.firebaseapp.myPackages.ui.compose.components.ProfileImage

@Composable
fun NotificationDetails(
    onBack:()->Unit,
    clickedNote: NoteContent?
){

    var note by remember { mutableStateOf<NoteContent?>(null) }
    LaunchedEffect(Unit) {
        note = clickedNote
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProfileImage(
                imageUrl = note?.user?.profileImage,
                size = 60
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp, end = 4.dp)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = note?.user?.userName ?: "",
                    fontSize = 16.sp,
                    fontWeight = Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = note?.date ?: "",
                    fontSize = 10.sp,
                    fontStyle = Italic,
                    color = Color.White
                )
            }
        }
       Column(modifier = Modifier.padding(24.dp)){
           Text(
               text = stringResource(R.string.title),
               fontSize = 16.sp,
               fontStyle = Italic,
               color = Color(0xFF558B2F)
           )
           Spacer(modifier = Modifier
               .fillMaxWidth()
               .height(4.dp))
           Text(
               text = note?.title?:"",
               fontSize = 13.sp,
               fontStyle = Normal,
               color = Color.White
           )
           Spacer(modifier = Modifier
               .fillMaxWidth()
               .height(10.dp))

           Text(
               text = stringResource(R.string.content),
               fontSize = 16.sp,
               fontStyle = Italic,
               color = Color(0xFF558B2F)
           )
           Spacer(modifier = Modifier
               .fillMaxWidth()
               .height(4.dp))

           Text(
               text = note?.note?:"",
               fontSize = 13.sp,
               fontStyle = Normal,
               color = Color.White
           )
       }
    }
}