package com.example.firebaseapp.myPackages.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.firebaseapp.R

@Composable
fun ProfileImage(imageUrl:String? = null,size:Int = 45,isLoading:Boolean = false) {

    if(isLoading){
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .border(width = 1.dp, color = Color(0xFF69F805), shape = CircleShape)
                .background(Color(0xFF558B2F)),
            contentAlignment = Alignment.Center
        ){
            LoadingView()
        }
    } else if(imageUrl!=null){
        ProgressiveProfileImage(
            size = size,
            imageUrl = imageUrl
        )
    }else{
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .border(width = 1.dp, color = Color(0xFF69F805), shape = CircleShape)
                .background(Color(0xFF558B2F)),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(R.drawable.notephoto),
                contentDescription = null,
            )
        }
    }
}