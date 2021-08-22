package com.qavan.app.di.module

import com.qavan.app.data.repository.*
import com.qavan.app.data.source.local.UserDataSource
import com.qavan.app.data.source.remote.BloggersDataSource
import com.qavan.app.data.source.remote.EventsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userDataSource: UserDataSource,
    ): UserRepository {
        return UserRepositoryImpl(
            userDataSource = userDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideEventsRepository(
        eventsDataSource: EventsDataSource,
    ): EventsRepository {
        return EventsRepositoryImpl(
            source = eventsDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideBloggersRepository(
        bloggersDataSource: BloggersDataSource,
    ): BloggersRepository {
        return BloggersRepositoryImpl(
            source = bloggersDataSource,
        )
    }

}