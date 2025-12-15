package com.example.firebaseapp.myPackages.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.getUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    profileImage:String?,
    notificationCount: Int = 4,
    onLogoutClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.khatera),
                color = Color.White,
                fontStyle = Italic,
                fontWeight = Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF558B2F)
        ),
        actions = {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .clickable() {
                        onProfileClick()
                    }
                    .shadow(
                        elevation = 16.dp,
                        shape = CircleShape,
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    ),
                contentAlignment = Alignment.Center
            ) {
                ProfileImage(
                    imageUrl = profileImage,
                    size = 34
                )
            }

            if (notificationCount > 0) {
                BadgedBox(
                    badge = { Badge { Text(notificationCount.toString()) } },
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        tint = Color.White,
                        contentDescription = "Notifications",
                        modifier = Modifier
                            .padding(start = 14.dp)
                            .clickable {
                                onNotificationClick()
                            }
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    tint = Color.White,
                    contentDescription = "Notifications",
                    modifier = Modifier
                        .padding(start = 14.dp)
                        .clickable {
                            onNotificationClick()
                        }
                )
            }

            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Color.White,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 14.dp)
                    .clickable {
                        expanded = true
                    }
            )

            DropdownMenu(
                modifier = Modifier
                    .background(Color(0xFF212121)),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                //##Logout
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.logout),
                            color = Color.White
                        )
                    },
                    onClick = {
                        expanded = false
                        onLogoutClick()
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            tint = Color(0xFF558B2F),
                            contentDescription = "Logout"
                        )
                    }
                )
            }
        }
    )

}
