package com.example.firebaseapp.myPackages.data.remote.firebase.database.repoImp

import android.util.Log
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.getUser
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.updateUser
import com.example.firebaseapp.myPackages.data.models.NoteContent
import com.example.firebaseapp.myPackages.data.models.User
import com.example.firebaseapp.myPackages.data.remote.firebase.database.repo.DataRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resumeWithException

class DataRepoImp() : DataRepo {
    var userId: String = ""
    private var userRef: DatabaseReference? = null
    private var notesRef: DatabaseReference? = null
    private var usersRef: DatabaseReference? = null
    private var sharedNotesRef: DatabaseReference? = null
    private var notesListener: ValueEventListener? = null


    init {
        val database = Firebase.database
        userId = getUser()?.id ?: ""
        notesRef = database.getReference("Notes")
        userRef = notesRef?.child(userId)
        sharedNotesRef = database.getReference("shared_notes")
        usersRef = database.getReference("Users")
    }

    //=====================================================
    override fun addNote(
        note: NoteContent,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val id = userRef?.push()?.key!!
        note.id = id
        userRef?.child(id)?.setValue(note)
            ?.addOnSuccessListener { onSuccess() }
            ?.addOnFailureListener { onFailure() }
    }

    //=====================================================
    override fun updateNote(
        note: NoteContent,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        userRef?.child(note.id!!)?.setValue(note)
            ?.addOnSuccessListener { onSuccess() }
            ?.addOnFailureListener { onFailure() }
    }

    //=====================================================
    override fun deleteNote(
        noteId: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        userRef?.child(noteId)?.removeValue()
            ?.addOnSuccessListener { onSuccess() }
            ?.addOnFailureListener { onFailure() }
    }

    //=====================================================
    override fun shareNote(
        noteId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: () -> Unit
    ) {
        val noteRef = userRef?.child(noteId) ?: return
        noteRef.child("isShared").get()
            .addOnSuccessListener { snapshot ->
                val isShared = snapshot.getValue(Boolean::class.java) ?: false
                if (isShared) {
                    onSuccess(true)
                    return@addOnSuccessListener
                }
                noteRef.child("isShared").setValue(true)
                    .addOnSuccessListener {
                        updateRemoteUser(
                            user = getUser().copy(
                                sharedNotesCount = getUser().sharedNotesCount + 1
                            ),
                            onSuccess = {
                                onSuccess(false)
                            },
                            onFailure = {
                                onFailure()
                            }
                        )
                    }.addOnFailureListener {
                        onFailure()
                    }
            }.addOnFailureListener {
                onFailure()
            }
    }


    //=====================================================
    override fun getUserNotes(
        onSuccess: (List<NoteContent>) -> Unit,
        onFailure: () -> Unit
    ) {
        notesRef?.child(userId)
            ?.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val notes = snapshot.children.mapNotNull {
                        it.getValue(NoteContent::class.java)
                    }
                    onSuccess(notes)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure()
                }
            })
    }

    //=====================================================
    override fun getUserSharedNotes(
        userId: String,
        onSuccess: (List<NoteContent>) -> Unit,
        onFailure: () -> Unit
    ) {
        notesRef?.child(userId)
            ?.orderByChild("isShared")
            ?.equalTo(true)
            ?.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val notes = snapshot.children.mapNotNull {
                        it.getValue(NoteContent::class.java)
                    }
                    onSuccess(notes)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure()
                }
            })
    }

    //=====================================================
    override fun getAllSharedNotes(
        scope: CoroutineScope,
        onSuccess: (List<NoteContent>) -> Unit,
        onFailure: () -> Unit
    ): Job {
        return scope.launch(Dispatchers.IO) {
            try {
                val userIds = suspendCancellableCoroutine { cont ->
                    getAllUserIds(
                        onSuccess = { ids -> cont.resume(ids) {} },
                        onFailure = { cont.resumeWithException(Exception("Failed to fetch user IDs")) }
                    )
                }

                val deferreds = userIds.map { userId ->
                    async {
                        suspendCancellableCoroutine { cont ->
                            getUserSharedNotes(
                                userId,
                                onSuccess = { notes -> cont.resume(notes) {} },
                                onFailure = { cont.resumeWithException(Exception("Failed for user $userId")) }
                            )
                        }
                    }
                }
                val results = deferreds.awaitAll().flatten()
                withContext(Dispatchers.Main) {
                    onSuccess(results)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onFailure()
                }
            }
        }
    }

    //=====================================================
    private fun getAllUserIds(
        onSuccess: (List<String>) -> Unit,
        onFailure: () -> Unit
    ) {
        notesRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userIds = snapshot.children.mapNotNull { it.key }
                onSuccess(userIds)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure()
            }
        })
    }

    //=====================================================
    override fun cancelRequest() {
        notesListener?.let {
            userRef?.removeEventListener(it)
            notesListener = null
        }
    }

    //======================================================
    override fun updateRemoteUser(
        user: User,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        usersRef?.child(user.id!!)?.setValue(user)
            ?.addOnSuccessListener {
                updateUser(user)
                onSuccess()
            }
            ?.addOnFailureListener { onFailure() }
    }


}
