package com.qavan.app.base.adapter

import android.view.View

interface SharedViewProvider<T: View> {
    val view: View
}