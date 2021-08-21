package com.qavan.app.data.source.remote

import androidx.paging.PagingSource
import io.ktor.client.*

abstract class BasePagingRemoteDataSource<T: Any>(
    httpClient: HttpClient,
): PagingSource<Int, T>() {

    protected val client: HttpClient = httpClient

}