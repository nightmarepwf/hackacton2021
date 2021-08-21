package com.qavan.app.extensions

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import java.util.*

val generateRandomColor: Int
    get() {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

val generateRandomColorDrawable: ColorDrawable
    get() {
        val rnd = Random()
        return ColorDrawable().apply {
            this.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }
    }