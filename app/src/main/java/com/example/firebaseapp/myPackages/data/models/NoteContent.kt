package com.example.firebaseapp.myPackages.data.models

class NoteContent{
    var id:String? = null
    var title:String? = null
    var note:String? = null
    var date:String? = null
    var user:User? = null
    var isShared :Boolean = false
   constructor(){}
    constructor(title:String, note:String, date: String,user:User)
    {
        this.title = title
        this.note = note
        this.date = date
        this.user = user
    }
    constructor(id:String,title:String, note:String, date: String,user:User)
    {
        this.id = id
        this.title = title
        this.note = note
        this.date = date
        this.user = user
    }

}