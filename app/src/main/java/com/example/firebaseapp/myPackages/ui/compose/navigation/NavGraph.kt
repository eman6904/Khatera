package com.example.firebaseapp.myPackages.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firebaseapp.myPackages.data.models.NoteContent
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
                onItemDetailsNav = { note ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("note", note)
                    navController.navigate("DETAILS")
                }
            )
        }

        composable("DETAILS") {
            val note = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<NoteContent>("note")

            NotificationDetails(
                clickedNote = note,
                onBack = { navController.popBackStack() }
            )
        }
    }
}


