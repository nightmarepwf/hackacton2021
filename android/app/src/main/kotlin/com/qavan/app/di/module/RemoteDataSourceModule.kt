package com.qavan.app.di.module

import com.qavan.app.data.source.remote.BloggersDataSource
import com.qavan.app.data.source.remote.EventsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideEventsDataSource(
        httpClient: HttpClient,
    ): EventsDataSource {
        return EventsDataSource(
            httpClient = httpClient,
        )
    }

    @Provides
    @Singleton
    fun provideBloggersDataSource(
        httpClient: HttpClient,
    ): BloggersDataSource {
        return BloggersDataSource(
            httpClient = httpClient,
        )
    }


}