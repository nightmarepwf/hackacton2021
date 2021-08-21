package com.qavan.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BloggersResponse(
    @SerialName("old_blogers")
    val oldBloggers: List<Blogger> = emptyList(),
    @SerialName("new_blogers")
    val newBloggers: BloggerNewHolder? = BloggerNewHolder(emptyList()),
)
