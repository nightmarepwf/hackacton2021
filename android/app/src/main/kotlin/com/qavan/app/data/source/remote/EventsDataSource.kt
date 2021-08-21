package com.qavan.app.data.source.remote

import androidx.paging.PagingState
import com.qavan.app.data.model.Event
import io.ktor.client.*
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class EventsDataSource(
    override val json: Json,
    override val httpClient: HttpClient,
): BasePagingRemoteDataSource<Event>() {

    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        return try {
            delay(700)
            val nextPageNumber = params.key ?: 1
            return LoadResult.Page(
                data = getEvents(nextPageNumber, params.loadSize),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    private fun getEvents(page: Int, count: Int): List<Event> {
        return ((page - 1) * count .. page * count).map {
            Event(
                id = it,
                title = "Event $it name",
                description = "Event $it description",
            )
        }
    }
}