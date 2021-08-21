package com.qavan.app.extensions.android

import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat

fun NotificationManager.notificationAvailable(
    channelId: String,
): Boolean {
    if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) !areNotificationsEnabled() else false)
        return false
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = getNotificationChannel(channelId) ?: return false
        if (channel.importance == NotificationManagerCompat.IMPORTANCE_NONE)
            return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val channelGroup = channel.group?.let { getNotificationChannelGroup(it) }
            when {
                channelGroup != null && channelGroup.isBlocked -> return false
            }
        }
    }
    return true
}