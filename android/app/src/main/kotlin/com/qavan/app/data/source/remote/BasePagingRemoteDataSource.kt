package com.qavan.app.data.source.remote

import androidx.paging.PagingSource
import com.qavan.app.data.constants.BASE_URL
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

abstract class BasePagingRemoteDataSource<T: Any>: PagingSource<Int, T>() {
    abstract val json: Json
    abstract val httpClient: HttpClient

    protected suspend inline fun <reified T> getAndDeserialize(url: String): T {
        return json.decodeFromString(
            httpClient.get<String>(url)
                .replace("]\"", "]")
                .replace("\"[", "[")
                .replace("\\\"","\"")
        )
    }
}