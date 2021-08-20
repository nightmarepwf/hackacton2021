package com.qavan.app.base.serviceabstractions

import android.app.NotificationManager
import android.content.Context
import com.google.firebase.messaging.RemoteMessage
import com.qavan.app.data.source.local.PushDataSource
import com.qavan.app.extensions.fetchNotification

class GmsPushProvider(
    context: Context,
    tokenDataSource: PushDataSource,
    notificationManager: NotificationManager,
): AbstractPushProvider<RemoteMessage>(context, tokenDataSource,notificationManager) {

    override fun onMessage(message: RemoteMessage) {
        val notification = message.data.fetchNotification(context, notificationManager) ?: return
        notificationManager.notify(message.messageId.hashCode(), notification)
    }

}