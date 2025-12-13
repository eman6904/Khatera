package com.example.firebaseapp.myPackages.data.local

import com.example.firebaseapp.myPackages.data.models.User
import com.orhanobut.hawk.Hawk

class UserData {
    companion object{
        fun saveUser(user: User) {
            Hawk.put("currentUser", user)
        }

        fun getUser(): User? {
            return Hawk.get("currentUser")
        }

        fun updateUser(newUser: User) {
            Hawk.put("currentUser", newUser)
        }

    }
}