package com.example.firebaseapp.myPackages.data.models

data class User(
    var id:String? = null,
    var userName:String? = null,
    var email:String? = null,
    var password:String? = null,
    var joinDate:String? = null,
    var profileImage:String? = null,
    var sharedNotesCount : Int = 0
)
