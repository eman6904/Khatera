package com.example.firebaseapp.myPackages.ui.compose.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                    navController.currentBackStackEntry?.arguments?.putParcelable("note", note)
                    navController.navigate("DETAILS")
                }
            )
        }


        composable("DETAILS") { backStackEntry ->
            val note = backStackEntry.arguments?.getParcelable<NoteContent>("note")
            NotificationDetails(
                note = note,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

