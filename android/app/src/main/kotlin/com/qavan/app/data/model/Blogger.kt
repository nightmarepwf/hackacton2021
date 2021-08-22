package com.qavan.app.data.model

import android.os.Parcelable
import com.qavan.app.extensions.EMPTY
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface IBlogger {
    val id: Int
    val title: String
    val titleShort: String
    val inst: String
    val yt: String
    val mail: String
    val rating: Int?
}

@Serializable
@Parcelize
data class Blogger(
    @SerialName("ID")
    override val id: Int = 0,
    val u_name: String? = " ",
    val u_soname: String? = " ",
    val u_otch: String? = " ",
    val email: String? = "Email не указан",
    val instagram: String? = "Instagram не указан",
    val youtube: String? = "Youtube не указан",
    val date_last_reject: String? = null,
    override val rating: Int? = null,
) : Parcelable, IBlogger {
    override val title: String
        get() = buildString {
            u_name?.let { append(it);append(" ") }
            u_soname?.let { append(it);append(" ") }
            u_otch?.let { append(it) }
        }

    override val titleShort: String
        get() = buildString {
            u_name?.firstOrNull()?.let { append(it) }
            u_soname?.firstOrNull()?.let { append(it) }
            u_otch?.firstOrNull()?.let { append(it) }
        }

    override val inst: String
        get() {
            return if (instagram == null || instagram.isBlank()) "Не указан" else instagram
        }

    override val yt: String
        get() {
            return if (youtube == null || youtube.isBlank()) "Не указан" else youtube
        }

    override val mail: String
        get() {
            return if (email == null || email.isBlank()) "Не указан" else email
        }

}
