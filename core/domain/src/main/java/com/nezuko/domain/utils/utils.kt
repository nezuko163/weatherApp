package com.nezuko.domain.utils

import android.util.Log
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

fun Int.toCelsius() = "${this}°"

private val TAG = "TIME_UTIL"

fun day(string: String): LocalDateTime {
    Log.i(TAG, "day: $string")
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(string, formatter).atStartOfDay()
}

fun dayWithTime(string: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return LocalDateTime.parse(string, formatter)!!
}

fun time(string: String): LocalDateTime {
    try {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        return LocalTime.parse(string, formatter)!!.atDate(LocalDate.now())
    } catch (e: Exception) {
        return LocalDateTime.now()
    }
}