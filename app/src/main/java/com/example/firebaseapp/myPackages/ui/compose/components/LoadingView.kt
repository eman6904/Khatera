package com.example.firebaseapp.myPackages.ui.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.firebaseapp.R

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    sz:Int = 20,
    strkW:Int = 2,
    color:Color = Color.White
) {
    CircularProgressIndicator(
        color = color,
        strokeWidth = strkW.dp,
        modifier = modifier.size(sz.dp)
          //  .padding(10.dp)
    )
}

