package com.example.firebaseapp.myPackages.data.remote.firebase.database.repoImp

import android.util.Log
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.getUser
import com.example.firebaseapp.myPackages.data.models.NoteContent
import com.example.firebaseapp.myPackages.data.remote.firebase.database.repo.DataRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DataRepoImp() : DataRepo {
    var userId:String = ""
    private var userRef: DatabaseReference? = null
    private var notesRef: DatabaseReference? = null
    private var sharedNotesRef: DatabaseReference? = null
    private var notesListener: ValueEventListener? = null


    init {
          val database = Firebase.database
          userId = getUser()?.id?:""
          notesRef = database.getReference("Notes")
          userRef = notesRef?.child(userId)
          sharedNotesRef = database.getReference("shared_notes")
    }

    override fun addNote(
        note: NoteContent,
        onSuccess: () -> Unit,
        onFailer: () -> Unit
    ) {
        val id =  userRef?.push()?.key!!
        note.id =  id
        userRef?.child(id)?.setValue(note)
            ?.addOnSuccessListener { onSuccess() }
            ?.addOnFailureListener { onFailer() }
    }

    override fun updateNote(
        note: NoteContent,
        onSuccess: () -> Unit,
        onFailer: () -> Unit
    ) {
        userRef?.child(note.id!!)?.setValue(note)
            ?.addOnSuccessListener { onSuccess() }
            ?.addOnFailureListener { onFailer() }
    }

    override fun deleteNote(
        note: NoteContent,
        onSuccess: () -> Unit,
        onFailer: () -> Unit
    ) {
        userRef?.child(note.id!!)?.removeValue()
            ?.addOnSuccessListener { onSuccess() }
            ?.addOnFailureListener { onFailer() }
    }

    override fun shareNote(
        note: NoteContent,
        onSuccess: () -> Unit,
        onFailer: () -> Unit
    ) {
        val id = sharedNotesRef?.push()?.key!!
        note.id = id
        sharedNotesRef?.child(id)?.setValue(note)
            ?.addOnSuccessListener { onSuccess() }
            ?.addOnFailureListener { onFailer() }
    }

    override fun getNotes(
        onSuccess: (List<NoteContent>) -> Unit,
        onFailer: () -> Unit
    ) {
        notesListener = object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val notesList = mutableListOf<NoteContent>()

                    for (noteSnapshot in snapshot.children) {
                        val note = noteSnapshot.getValue(NoteContent::class.java)
                        note?.let { notesList.add(it) }
                    }
                    onSuccess(notesList)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailer()
                }
            }
        userRef?.addListenerForSingleValueEvent(notesListener!!)
    }

    fun cancelRequest() {
        notesListener?.let {
            userRef?.removeEventListener(it)
            notesListener = null
        }
    }

}
