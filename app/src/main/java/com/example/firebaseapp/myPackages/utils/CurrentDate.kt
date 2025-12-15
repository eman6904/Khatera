package com.example.firebaseapp.myPackages.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


//to calculate current date
fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(
        "MMMM yyyy - EEEE h:mm a",
        Locale.ENGLISH
    )
    return dateFormat.format(calendar.time)
}

