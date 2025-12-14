package com.example.firebaseapp.myPackages.data.remote.firebase.auth.repoImp

import android.content.Context
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.local.UserData.Companion.saveUser
import com.example.firebaseapp.myPackages.data.remote.firebase.auth.repo.AuthRepo
import com.example.firebaseapp.myPackages.data.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AuthRepoImpl(val context: Context) : AuthRepo {
    private var authRef: FirebaseAuth? = null
    private var userRef: DatabaseReference? = null

    init {
        var database = Firebase.database
        userRef = database.getReference("Users").push()
        authRef = FirebaseAuth.getInstance()
    }

    //=====================================================
    override fun signUp(
        user: User,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        authRef?.createUserWithEmailAndPassword(
            user.email!!,
            user.password!!
        )?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful) {
                    //email and password are written correctly
                    sendEmailVerification(
                        onSuccess = {
                            user.id = userRef?.key ?: ""
                            userRef?.setValue(user)
                            saveUser(user)
                            onSuccess()
                        },
                        onFailure = { onFailure(it) }
                    )

                } else {
                    // email or password is not written correctly
                    onFailure(context.getString(R.string.email_or_password_is_not_written_correctly))
                }
            }
        })
    }

    //=====================================================
    override fun sendEmailVerification(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        //send message to verify email
        authRef?.currentUser?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                //Message has been sent successfully you must move to login page to verify email
                onSuccess()
            } else {
                onFailure(context.getString(R.string.something_went_wrong_try_again))
            }
        }
    }

    //=====================================================
    override fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        authRef?.signInWithEmailAndPassword(
            email,
            password
        )?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful) {
                    //This account already exists
                    verifyEmailAddress(
                        onSuccess = { onSuccess() },
                        onFailure = { onFailure(it) }
                    )
                } else {
                    onFailure(context.getString(R.string.email_or_password_incorrect))
                }
            }
        })
    }

    //=====================================================
    override fun verifyEmailAddress(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        //to verify email
        if (authRef?.currentUser!!.isEmailVerified) {
            onSuccess()
        } else {
            onFailure(context.getString(R.string.please_verify_your_account))
        }
    }
}