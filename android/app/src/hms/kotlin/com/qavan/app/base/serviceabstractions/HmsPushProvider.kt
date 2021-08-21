package com.qavan.app.base.serviceabstractions

import android.app.NotificationManager
import android.content.Context
import com.huawei.hms.push.RemoteMessage
import com.qavan.app.data.source.local.PushDataSource
import com.qavan.app.extensions.fetchNotification

class HmsPushProvider(
    context: Context,
    pushDataSource: PushDataSource,
    notificationManager: NotificationManager,
): AbstractPushProvider<RemoteMessage>(context, pushDataSource,notificationManager) {

    override fun onMessage(message: RemoteMessage) {
        val notification = message.dataOfMap.fetchNotification(context, notificationManager) ?: return
        notificationManager.notify(message.messageId.hashCode(), notification)
    }
}