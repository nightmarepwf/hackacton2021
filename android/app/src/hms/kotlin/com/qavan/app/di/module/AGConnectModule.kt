package com.qavan.app.di.module

import com.huawei.agconnect.crash.AGConnectCrash
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AGConnectModule {

    @Provides
    @Singleton
    fun provideAGConnectCrash(): AGConnectCrash = AGConnectCrash.getInstance()

}