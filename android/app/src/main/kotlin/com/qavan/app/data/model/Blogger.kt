package com.qavan.app.data.model

import android.os.Parcelable
import com.qavan.app.extensions.EMPTY
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Blogger(
    @SerialName("ID")
    val ID: Int = 0,
    val u_name: String? = String.EMPTY,
    val u_soname: String? = String.EMPTY,
    val u_otch: String? = String.EMPTY,
    val email: String? = "Email не указан",
    val instagram: String? = "Instagram не указан",
    val youtube: String? = "Youtube не указан",
    val date_last_reject: String? = null,
) : Parcelable
