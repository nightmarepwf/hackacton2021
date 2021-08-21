package com.qavan.app.data.source.remote

import androidx.paging.PagingState
import com.qavan.app.data.constants.BASE_URL
import com.qavan.app.data.model.Event
import com.qavan.app.extensions.date
import io.ktor.client.*
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import timber.log.Timber

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
            if (nextPageNumber > 1) return LoadResult.Error(NotImplementedError())
            return LoadResult.Page(
                data = getEvents(nextPageNumber, params.loadSize),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Throwable) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    private suspend fun getEvents(page: Int, count: Int): List<Event> {
        return getAndDeserialize<List<Event>>("${BASE_URL}/Events").map {
            it.copy(event_date_formatted = it.event_date_formatted.date)
        }
    }
}