package com.example.firebaseapp.myPackages.ui.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.firebaseapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    notificationCount: Int = 4,
    onLogoutClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
            text = "Home", color = Color.White
        ) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF558B2F)
        ),
        actions = {
                if (notificationCount > 0) {
                    BadgedBox(
                        badge = { Badge { Text(notificationCount.toString()) } },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            tint = Color.White,
                            contentDescription = "Notifications",
                            modifier = Modifier.clickable{
                                onNotificationClick()
                            }
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        tint = Color.White,
                        contentDescription = "Notifications"
                    )
                }

            Icon(
                imageVector = Icons.Default.MoreVert,
                tint = Color.White,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clickable {
                    expanded = true
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        expanded = false
                        onLogoutClick()
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            contentDescription = "Logout"
                        )
                    }
                )
            }
        }
    )

}
