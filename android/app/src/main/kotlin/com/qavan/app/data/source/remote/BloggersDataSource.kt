package com.qavan.app.data.source.remote

import androidx.paging.PagingState
import com.qavan.app.data.constants.BASE_URL
import com.qavan.app.data.model.Blogger
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

class BloggersDataSource(
    override val json: Json,
    override val httpClient: HttpClient,
): BasePagingRemoteDataSource<Blogger>() {

    override fun getRefreshKey(state: PagingState<Int, Blogger>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Blogger> {
        return try {
            delay(700)
            val nextPageNumber = params.key ?: 1
            if (nextPageNumber > 1)
                return  LoadResult.Error(NotImplementedError())
            return LoadResult.Page(
                data = getBloggers(nextPageNumber, params.loadSize),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    private suspend fun getBloggers(page: Int, count: Int): List<Blogger> {
        return getAndDeserialize("${BASE_URL}/Blogers")
    }
}