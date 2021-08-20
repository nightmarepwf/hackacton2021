package com.qavan.app.di.module

import com.qavan.app.data.repository.UserRepository
import com.qavan.app.data.repository.UserRepositoryImpl
import com.qavan.app.data.source.local.UserDataSource
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

}