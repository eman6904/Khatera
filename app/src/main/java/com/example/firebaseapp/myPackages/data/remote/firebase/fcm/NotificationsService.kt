package com.example.firebaseapp.myPackages.data.remote.firebase.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.models.NoteContent
import com.example.firebaseapp.myPackages.ui.xml.fragments.Note
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // This function is called whenever a new FCM message is received
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        remoteMessage.notification?.let { notif ->
            showSimpleNotification(notif.title ?: "", notif.body ?: "")
        }

        if (remoteMessage.data.isNotEmpty()) {
//            val note = NoteContent(
//                id = remoteMessage.data["id"]?:"0",
//                title = remoteMessage.data["title"] ?: "No title",
//                note = remoteMessage.data["content"] ?: "No content",
//                date = remoteMessage.data["date"] ?: "Unknown"
//            )
        }
    }
    // Function to create and show a simple notification in the Status Bar

    private fun showSimpleNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, "general_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
        notify(builder)
    }

    // Helper function to send the notification
    private fun notify(
        builder: NotificationCompat.Builder,
        id: Int = System.currentTimeMillis().toInt(),
        channelId: String = "general_channel",
        channelName: String = "General Notification"
    ) {
        //val manager = getSystemService(NotificationManager::class.java)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(id, builder.build())
    }
}
