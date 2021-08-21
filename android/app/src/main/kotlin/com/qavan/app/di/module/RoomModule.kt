package com.qavan.app.di.module

import android.content.Context
import androidx.room.Room
import com.qavan.app.BuildConfig
import com.qavan.app.data.database.AppDatabase
import com.qavan.app.data.database.converters.TagConverter
import com.qavan.app.data.database.converters.UriConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "${BuildConfig.PROJECT}.db" )
        .addTypeConverter(UriConverter())
        .addTypeConverter(TagConverter())
        .build()

}