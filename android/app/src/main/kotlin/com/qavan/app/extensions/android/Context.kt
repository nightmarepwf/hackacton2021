package com.qavan.app.extensions.android

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.qavan.app.extensions.EMPTY

fun Context.copyText(
    text: String,
    textDescription: String = String.EMPTY,
) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText(textDescription, text))
}

fun Context.dpToPx(dp:Int): Float {
    return dp.toFloat() * this.resources.displayMetrics.density
}