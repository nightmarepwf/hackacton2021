package com.qavan.app.data.repository

import com.qavan.app.data.source.remote.EventsDataSource

interface EventsRepository {
    val eventsDataSource: EventsDataSource
}

class EventsRepositoryImpl(
    override val eventsDataSource: EventsDataSource,
): BaseRepository(), EventsRepository {

}