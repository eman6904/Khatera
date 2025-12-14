package com.example.firebaseapp.myPackages.utils

import android.content.Context
import android.widget.Toast
import com.example.firebaseapp.R

 fun showError(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.something_went_wrong_try_again), Toast.LENGTH_LONG
    ).show()
}