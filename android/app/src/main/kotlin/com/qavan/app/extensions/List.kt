package com.qavan.app.extensions

operator fun <T> List<T>.times(times: Int): List<T> {
    val r = ArrayList<T>(this)
    (0 until times).forEach { _ -> r.addAll(this) }
    return r
}