package com.example.firebaseapp.myPackages.data.remote.firebase.storage.repo

import android.net.Uri

interface StorageRepo {
    fun uploadProfileImage(
        imageUri: Uri,
        onSuccess: (downloadUrl: String) -> Unit,
        onFailure: () -> Unit
    )
}