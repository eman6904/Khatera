package com.example.firebaseapp.myPackages.data.remote.firebase.fcm

import com.example.firebaseapp.myPackages.data.models.NoteContent

//suspend fun sendNoteNotification(note: NoteContent) {
//    val url = "https://fcm.googleapis.com/fcm/send"
//    val serverKey = "key=YOUR_SERVER_KEY" // هتحطي Server Key من Firebase
//
//    val json = """
//        {
//            "to": "/topics/all_users",
//            "notification": {
//                "title": "${note.title}",
//                "body": "${note.note}"
//            },
//            "data": {
//                "id": "${note.id}",
//                "title": "${note.title}",
//                "content": "${note.note}",
//                "date": "${note.date}"
//            }
//        }
//    """.trimIndent()
//
//    val client = OkHttpClient()
//    val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
//    val request = Request.Builder()
//        .url(url)
//        .post(body)
//        .addHeader("Authorization", serverKey)
//        .build()
//
//    client.newCall(request).execute().use { response ->
//        Log.d("FCM", "Response: ${response.body?.string()}")
//    }
//}
