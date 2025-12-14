package com.example.firebaseapp.myPackages.data.remote.firebase.auth.repo

import com.example.firebaseapp.myPackages.data.models.User

interface AuthRepo {

   /*-----------SIGN UP-----------*/
    fun signUp(
        user: User,
        onSuccess:()->Unit,
        onFailure:(String)->Unit
        )

    /*-----------SEND EMAIL VERIFICATION-----------*/
    fun sendEmailVerification(
        onSuccess:()->Unit,
        onFailure:(String)->Unit
    )

    /*-----------SIGN IN-----------*/
    fun signIn(
        email:String,
        password:String,
        onSuccess:()->Unit,
        onFailure:(String)->Unit
    )

    /*-----------VERIFY EMAIL-----------*/
    fun verifyEmailAddress(
        onSuccess:()->Unit,
        onFailure:(String)->Unit
    )
}