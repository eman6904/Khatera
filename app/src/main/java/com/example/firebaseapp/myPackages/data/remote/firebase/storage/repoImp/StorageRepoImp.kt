package com.example.firebaseapp.myPackages.data.remote.firebase.storage.repoImp
import android.net.Uri
import android.util.Log
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.getUser
import com.example.firebaseapp.myPackages.data.remote.firebase.storage.repo.StorageRepo
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class StorageRepoImp : StorageRepo {

    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference

    override fun uploadProfileImage(
        imageUri: Uri,
        onSuccess: (downloadUrl: String) -> Unit,
        onFailure: () -> Unit
    ) {
        val userId = getUser().id
        val profileRef = storageRef.child("profile_images/$userId.jpg")

        profileRef.putFile(imageUri)
            .addOnSuccessListener {
                profileRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }.addOnFailureListener {
                    onFailure()
                }
            }
            .addOnFailureListener {
                onFailure()
            }
    }
}
