package com.example.firebaseapp.myPackages.data.remote.firebase.database.repo

import com.example.firebaseapp.myPackages.data.models.NoteContent
import com.example.firebaseapp.myPackages.data.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface DataRepo {
    /* ---------- Add / Update / Delete ---------- */
    fun addNote(
        note: NoteContent,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun updateNote(
        note: NoteContent,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun deleteNote(
        noteId: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    /* ---------- Share ---------- */

    fun shareNote(
        noteId: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: () -> Unit
    )

    /* ---------- Get Notes ---------- */

    fun getUserNotes(
        onSuccess: (List<NoteContent>) -> Unit,
        onFailure: () -> Unit
    )

    fun getUserSharedNotes(
        userId: String,
        onSuccess: (List<NoteContent>) -> Unit,
        onFailure: () -> Unit
    )

    fun getAllSharedNotes(
        scope: CoroutineScope,
        onSuccess: (List<NoteContent>) -> Unit,
        onFailure: () -> Unit
    ): Job

    /* ---------- Cancel ---------- */

    fun cancelRequest()

    /* ---------- UpdateUser ---------- */
    fun updateRemoteUser(
        user: User,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

}