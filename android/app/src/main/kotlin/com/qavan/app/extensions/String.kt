package com.qavan.app.extensions

import android.util.Patterns
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

inline val String.Companion.EMPTY: String
    get() = ""

inline val String.uppercaseFirst: String
    get() = replaceFirstChar { it.uppercase() }

operator fun String.times(count: Int): String {
    var r = this
    for (i in 0 until count - 1) {
        r += this
    }
    return r
}

fun String.times(count: Int, separator: String = String.EMPTY): String {
    var r = this
    for (i in 0 until count - 1) {
        r += separator + this
    }
    return r
}

inline val String.isEmail: Boolean
    get() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

inline val String.date: String
    get() {
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date.time
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$day.${
            if (month < 10)
                "0$month"
            else
                month
        }.$year"
    }