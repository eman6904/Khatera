package com.example.firebaseapp.myPackages.ui.compose.components
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProgressiveProfileImage(
    imageUrl: String?,
    size: Int = 100
) {
    var isLoaded by remember { mutableStateOf(false) }

    val revealProgress by animateFloatAsState(
        targetValue = if (isLoaded) 0f else 1f,
        animationSpec = tween(durationMillis = 800),
        label = ""
    )

    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .listener(
                    onSuccess = { _, _ ->
                        isLoaded = true
                    }
                )
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    scaleY = revealProgress
                    transformOrigin = TransformOrigin(0.5f, 1f)
                }
                .background(Color(0xFF558B2F))
        )
    }
}
