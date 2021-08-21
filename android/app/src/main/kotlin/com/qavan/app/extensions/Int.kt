package com.qavan.app.extensions

import android.content.res.Resources

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()