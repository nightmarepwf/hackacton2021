package com.qavan.app.base.serviceabstractions

import android.app.NotificationManager
import android.content.Context
import android.os.Parcelable
import com.qavan.app.data.source.local.PushDataSource

fun interface MessageReceivingListener<T> where T: Parcelable {
    fun onMessage(message: T)
}

abstract class AbstractPushProvider<T>(
    protected val context: Context,
    private val pushDataSource: PushDataSource,
    protected val notificationManager: NotificationManager,
): MessageReceivingListener<T> where T: Parcelable {

    var token: String
        get() = pushDataSource.currentToken
        set(value) {
            pushDataSource.currentToken = value
        }

}