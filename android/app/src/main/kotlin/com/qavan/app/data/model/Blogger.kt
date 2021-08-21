package com.qavan.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Blogger(
    val id: Int = 0,
    val name: String = "Blogger name",
    val instagram: String = "@login",
    val rating: Float = 5f,
    val email: String = "test@qavan.xyz",
) : Parcelable
