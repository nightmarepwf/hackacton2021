package com.qavan.app.extensions.view

import com.google.android.material.textfield.TextInputEditText

inline val TextInputEditText.string: String
    get() = text.toString()

inline val TextInputEditText.blank: Boolean
    get() = text.isNullOrBlank()