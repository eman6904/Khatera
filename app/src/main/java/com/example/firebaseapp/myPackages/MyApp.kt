package com.example.firebaseapp.myPackages

import android.app.Application
import com.orhanobut.hawk.Hawk

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }
}
