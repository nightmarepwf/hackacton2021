package com.qavan.app.extensions.view

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.widget.TextView

fun TextView.onIme(
    vararg imeCode: Int,
    action: (Int, KeyEvent?) -> Unit = { _: Int, _: KeyEvent? -> },
) {
    setOnEditorActionListener { _, actionId, keyEvent ->
        return@setOnEditorActionListener when(keyEvent) {
            null -> {
                action(actionId, null)
                false
            }
            else -> when (actionId) {
                in imeCode -> {
                    action(actionId, keyEvent)
                    true
                }
                else -> false
            }
        }
    }
}

@SuppressLint("SetTextI18n")
fun TextView.appendText(
    vararg payload: String,
) {
    val sep = "\n"
    text = text.toString() + sep + payload.joinToString(sep)
}