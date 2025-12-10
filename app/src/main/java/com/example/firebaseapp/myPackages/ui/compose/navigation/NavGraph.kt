package com.example.firebaseapp.myPackages.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebaseapp.myPackages.ui.compose.screens.NotificationDetails
import com.example.firebaseapp.myPackages.ui.compose.screens.Notifications

@Composable
fun ComposeNavGraph() {
    val navController = rememberNavController()

   NavHost(
        navController = navController,
        startDestination = "NOTIFICATIONS"
    ) {

        composable("NOTIFICATIONS") {
            Notifications(
                onItemDetailsNav = {
                    navController.navigate("Details")
                }
            )
        }

        composable("Details") {
            NotificationDetails (
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
