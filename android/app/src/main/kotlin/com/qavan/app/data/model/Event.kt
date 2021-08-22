package com.qavan.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("ID")
    val id: Int = 0,
    val title: String = "Event title",
    val event_description: String = "Item description",
    val event_date: String = "",
    val event_date_formatted: String = "",
    val event_image: String = "https://sm.mashable.com/mashable_in/photo/default/nasa-galaxy_9pu4.jpg",
    @SerialName("count1")
    val event_participant_count: Int = 0,
)
