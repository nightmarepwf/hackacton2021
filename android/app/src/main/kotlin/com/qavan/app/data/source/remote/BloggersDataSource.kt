package com.qavan.app.data.source.remote

import androidx.paging.PagingState
import com.qavan.app.data.model.Blogger
import io.ktor.client.*
import kotlinx.coroutines.delay

class BloggersDataSource(
    httpClient: HttpClient,
): BasePagingRemoteDataSource<Blogger>(httpClient) {

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
            return LoadResult.Page(
                data = getBloggers(nextPageNumber, params.loadSize),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    private fun getBloggers(page: Int, count: Int): List<Blogger> {
        return ((page - 1) * count .. page * count).map {
            Blogger(
                id = it,
                name = "Noname $it",
                instagram = "@instagram$it",
                rating = (50..100).random() / 10f,
                email = "blogger$it@qavan.xyz",
            )
        }
    }
}