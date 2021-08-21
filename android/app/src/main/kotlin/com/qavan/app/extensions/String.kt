package com.qavan.app.extensions

import android.util.Patterns

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