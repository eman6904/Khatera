package com.example.firebaseapp.myPackages.data.remote.demoApi.repo

interface NotificationRepo {
    suspend fun sendSharedNoteNotification(
        title: String,
        body: String,
    ): Result<Unit>
}
