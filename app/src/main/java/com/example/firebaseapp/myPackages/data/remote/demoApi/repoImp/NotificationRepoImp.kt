package com.example.firebaseapp.myPackages.data.remote.demoApi.repoImp


import android.app.DownloadManager
import android.content.Context
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.remote.demoApi.repo.NotificationRepo
import com.google.auth.oauth2.GoogleCredentials
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class NotificationRepoImp(
    private val context: Context
) : NotificationRepo {

    override suspend fun sendSharedNoteNotification(
        title: String,
        body: String,
    ): Result<Unit> {
        return try {
            val token = getAccessToken(context)
            val json = buildRequestBody(title, body)
            sendRequest(token, json)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    private fun getAccessToken(context: Context): String {
        val stream = context.resources.openRawResource(R.raw.firebase_servicee)
        val credentials = GoogleCredentials.fromStream(stream)
            .createScoped(
                listOf("https://www.googleapis.com/auth/firebase.messaging")
            )
        credentials.refreshIfExpired()
        return credentials.accessToken.tokenValue
    }
    private fun buildRequestBody(
        title: String,
        body: String,
    ): String {
        val payload = mapOf(
            "message" to mapOf(
                "topic" to "all_users",
                "notification" to mapOf(
                    "title" to title,
                    "body" to body
                )
            )
        )
        return Gson().toJson(payload)
    }
    private fun sendRequest(token: String, json: String) {

        val client = OkHttpClient()

        val body = json.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://fcm.googleapis.com/v1/projects/YOUR_PROJECT_ID/messages:send")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        client.newCall(request).execute()
    }


}
