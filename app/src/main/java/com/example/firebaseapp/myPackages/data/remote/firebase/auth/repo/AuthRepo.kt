package com.example.firebaseapp.myPackages.data.remote.firebase.auth.repo

import com.example.firebaseapp.myPackages.data.models.User

interface AuthRepo {

    fun signUp(
        user: User,
        onSuccess:()->Unit,
        onFailer:(String)->Unit
        )

    fun sendEmailVerification(
        onSuccess:()->Unit,
        onFailer:(String)->Unit
    )

    fun signIn(
        email:String,
        password:String,
        onSuccess:()->Unit,
        onFailer:(String)->Unit
    )

    fun verifyEmailAddress(
        onSuccess:()->Unit,
        onFailer:(String)->Unit
    )
}