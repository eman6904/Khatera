package com.example.firebaseapp.myPackages.ui.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseapp.myPackages.data.models.NoteContent

@Composable
fun NotificationItem(note: NoteContent, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color.White.copy(alpha = 0.6f),
                spotColor = Color.White.copy(alpha = 0.6f)
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick() },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ProfileImage(
                    imageUrl = note.user?.profileImage
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp, end = 4.dp)
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = note.user?.userName ?: "",
                        fontSize = 16.sp,
                        fontWeight = Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(
                        text = note.title ?: "",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = note.date ?: "",
                        fontSize = 10.sp,
                        fontStyle = Italic,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}
