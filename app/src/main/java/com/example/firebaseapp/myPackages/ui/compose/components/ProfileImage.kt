package com.example.firebaseapp.myPackages.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.getUser

@Composable
fun ProfileImage(imageUrl:String? = null,size:Int = 45) {
    if(imageUrl!=null){
        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(R.drawable.image5),
            error = painterResource(R.drawable.image5),
            contentDescription = null,
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }else{
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(Color(0xFF558B2F)),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(R.drawable.notephoto),
                contentDescription = null,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}