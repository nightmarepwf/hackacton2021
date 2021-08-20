package com.qavan.app.di.module

import android.app.NotificationManager
import android.content.Context
import com.google.firebase.messaging.RemoteMessage
import com.qavan.app.base.serviceabstractions.AbstractPushProvider
import com.qavan.app.base.serviceabstractions.GmsPushProvider
import com.qavan.app.data.source.local.PushDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PushModule {

    @Provides
    @Singleton
    fun providePushProvider(
        context: Context,
        tokenDataSource: PushDataSource,
        notificationManager: NotificationManager,
    ): AbstractPushProvider<RemoteMessage> = GmsPushProvider(
        context = context,
        tokenDataSource = tokenDataSource,
        notificationManager = notificationManager,
    )
}