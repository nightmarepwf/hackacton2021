package com.qavan.app.di.module

import android.content.SharedPreferences
import com.qavan.app.data.source.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideDevicePreferences(sharedPreferences: SharedPreferences): DevicePreferencesDataSource {
        return DevicePreferencesDataSourceImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providePushDataSource(
        sharedPreferences: SharedPreferences,
    ): PushDataSource {
        return PushDataSourceImpl(
            sharedPreferences = sharedPreferences,
        )
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        sharedPreferences: SharedPreferences,
    ): UserDataSource {
        return UserDataSourceImpl(
            sharedPreferences = sharedPreferences,
        )
    }


}