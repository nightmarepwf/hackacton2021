package com.qavan.app.data.source.remote

import androidx.paging.PagingState
import com.qavan.app.data.constants.BASE_URL
import com.qavan.app.data.model.BloggersResponse
import com.qavan.app.data.model.IBlogger
import io.ktor.client.*
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import timber.log.Timber

class BloggersDataSource(
    override val json: Json,
    override val httpClient: HttpClient,
): BasePagingRemoteDataSource<IBlogger>() {

    override fun getRefreshKey(state: PagingState<Int, IBlogger>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IBlogger> {
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
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    private suspend fun getBloggers(page: Int, count: Int): List<IBlogger> {
        val response = getAndDeserialize<BloggersResponse>("${BASE_URL}/Blogers")
        return (response.oldBloggers.sortedByDescending { it.rating } + (response.newBloggers?.users ?: emptyList()))
    }
}