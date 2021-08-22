package com.qavan.app.data.model

import android.view.View
import kotlinx.serialization.Serializable

@Serializable
data class TagX(
    val id: Int = View.generateViewId(),
    val name: String,
)
