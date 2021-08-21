package com.qavan.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BloggerNewHolder(
    val users: List<BloggerNew>,
)

@Serializable
data class BloggerNew(
    @SerialName("pk")
    override val id: Int = 0,
    val full_name: String? = " ",
    @SerialName("username")
    val instagram: String = "Instagram не указан",
) : IBlogger {
    override val title: String
        get() = buildString {
            append(full_name)
        }

    override val titleShort: String
        get() = buildString {
            instagram.first()
        }

    override val inst: String
        get() = instagram

    override val yt: String
        get() = "Не указан"

    override val mail: String
        get() = "Не указан"

    override val rating: Int?
        get() = null

}