package com.qavan.app.data.source.remote

import io.ktor.client.*

abstract class BaseRemoteDataSource(
    httpClient: HttpClient,
) {

    protected val client: HttpClient = httpClient

}