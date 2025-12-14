package com.example.firebaseapp.myPackages.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteContent(
    var id: String? = null,
    var title: String? = null,
    var note: String? = null,
    var date: String? = null,
    var user: User? = null,
    var isShared: Boolean = false
) : Parcelable {

    constructor() : this(null)

    constructor(title: String, note: String, date: String, user: User) :
            this(null, title, note, date, user, false)

    constructor(id: String, title: String, note: String, date: String, user: User) :
            this(id, title, note, date, user, false)
}
