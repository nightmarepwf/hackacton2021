package com.qavan.app.data.repository

import com.qavan.app.data.source.remote.EventsDataSource

interface EventsRepository {
    val source: EventsDataSource
}

class EventsRepositoryImpl(
    override val source: EventsDataSource,
): BaseRepository(), EventsRepository {

}