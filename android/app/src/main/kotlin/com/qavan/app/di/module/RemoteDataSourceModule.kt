package com.qavan.app.di.module

import com.qavan.app.data.source.remote.BloggersDataSource
import com.qavan.app.data.source.remote.EventsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideEventsDataSource(
        httpClient: HttpClient,
        json: Json,
    ): EventsDataSource {
        return EventsDataSource(
            json = json,
            httpClient = httpClient,
        )
    }

    @Provides
    @Singleton
    fun provideBloggersDataSource(
        httpClient: HttpClient,
        json: Json,
    ): BloggersDataSource {
        return BloggersDataSource(
            json = json,
            httpClient = httpClient,
        )
    }


}