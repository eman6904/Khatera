package com.example.firebaseapp.myPackages.data.remote.firebase.database.repo

import com.example.firebaseapp.myPackages.data.models.NoteContent

interface DataRepo {
    fun addNote(
        note: NoteContent,
        onSuccess:()->Unit,
        onFailer:()->Unit
    )

    fun updateNote(
        note: NoteContent,
        onSuccess:()->Unit,
        onFailer:()->Unit
    )

    fun deleteNote(
        note: NoteContent,
        onSuccess:()->Unit,
        onFailer:()->Unit
    )

    fun shareNote(
        note: NoteContent,
        onSuccess:()->Unit,
        onFailer:()->Unit
    )

    fun getNotes(
        onSuccess: (List<NoteContent>) -> Unit,
        onFailer: () -> Unit
    )

}