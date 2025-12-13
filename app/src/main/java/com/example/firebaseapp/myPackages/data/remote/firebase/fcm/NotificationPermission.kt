package com.example.firebaseapp.myPackages.data.remote.firebase.fcm
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
@Composable
fun RequestNotificationPermission(
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var permissionRequested by remember { mutableStateOf(false) }

    var isFirstTime by remember { mutableStateOf(true) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
            isFirstTime = false
        } else if (isFirstTime.not()) {
            isFirstTime = false
            onPermissionDenied()
        }
        isFirstTime = false

    }

    LaunchedEffect(Unit) {
        // Check if Android version is 13+ and we haven't requested permission yet
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !permissionRequested) {
            permissionRequested = true // Mark that we requested permission to avoid requesting again

            val permission = Manifest.permission.POST_NOTIFICATIONS
            // Check if permission is already granted
            val alreadyGranted = ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED

            if (alreadyGranted) {
                // Permission is already granted → we can show notifications
                onPermissionGranted()
            }
            else if (activity?.shouldShowRequestPermissionRationale(permission) == false) {
                // First time requesting permission → launch the permission dialog
                permissionLauncher.launch(permission)
            }
            else {
                // User has previously denied permission → you can show a message or skip notifications
                onPermissionDenied()
            }
        }
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            // Android version < 13 → permission is not required, consider it granted
            onPermissionGranted()
        }
    }

}