package com.example.firebaseapp.myPackages.data.firebase.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

/**
 * This function retrieves the FCM registration token for this app instance.
 *
 * Use this when:
 * 1. You want to send notifications to a single device.
 * 2. You want to manage device groups or target individual users.
 * 3. You are sending notifications via your server or storing the token in Firebase.
 *
 * If you are using Topics instead, you may not need this token.
 */
fun getFCMToken(doAction: (String) -> Unit) {
    FirebaseMessaging.getInstance().token.addOnSuccessListener {
        doAction(it)
    }
}

/**
 * Note on Topics:
 * - Use Topics when you want to send notifications to multiple devices without tracking individual tokens.
 * - Each device can subscribe to one or more topics:
 *      FirebaseMessaging.getInstance().subscribeToTopic("all_users")
 * - Notifications sent to a topic reach all subscribed devices automatically.
 *
 * Summary:
 * - Single device → use getFCMToken().
 * - Multiple devices / broadcast → use Topics.
 */
fun subscribeToTopic(topic: String = "all_users") {
    FirebaseMessaging.getInstance().subscribeToTopic(topic)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM", "Subscribed to topic: $topic")
            } else {
                Log.w("FCM", "Failed to subscribe to topic: $topic", task.exception)
            }
        }
}
