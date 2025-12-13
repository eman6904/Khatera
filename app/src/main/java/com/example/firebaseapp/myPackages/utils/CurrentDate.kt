package com.example.firebaseapp.myPackages.utils

import java.text.SimpleDateFormat
import java.util.Calendar

//to calculate current date
fun getCurrentDate(): String {
    val calender = Calendar.getInstance()
    val mdformat = SimpleDateFormat("EEEE hh:mm a")
    val strDate = mdformat.format(calender.time)
    return strDate
}