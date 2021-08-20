package com.qavan.app.extensions

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.kirich1409.androidnotificationdsl.channels.createNotificationChannels
import com.qavan.app.R

inline val Map<String, String>.type: String
    get() = getOrElse("type") { TYPE_UNSPECIFIED }

inline val TYPE_UNSPECIFIED: String
    get() = "UNSPECIFIED"

private inline val TYPE_ANOTHER: String
    get() = "TYPE_ANOTHER"

fun Map<String, String>.fetchNotification(
    context: Context,
    notificationManager: NotificationManager,
): Notification? {
    val channelDefaultId = context.getString(R.string.app_name)
    createNotificationChannels(context) {
        channel(channelDefaultId, channelDefaultId, NotificationManagerCompat.IMPORTANCE_HIGH)
    }
    return when(type) {
        TYPE_UNSPECIFIED -> null
        TYPE_ANOTHER -> {
            TODO()
//            TODO EXAMPLE OF NOTIFICATION EXTENSIONS DSL USING
//            when (notificationManager.notificationAvailable(channelDefaultId)) {
//                true -> notification(
//                    context = context,
//                    channelId = channelDefaultId,
//                    smallIcon = R.drawable.ic_placeholder
//                ) {
//                    autoCancel(true)
//                    contentTitle("New!")
//                    contentText("New description!")
//                    contentIntent(
//                        context.deepLinkTo<AppActivity>(
//                            navGraph = R.navigation.navigation_tabs,
//                            destinationId = R.id.fragment_items,
//                            args = bundleOf(),
//                        )
//                    )
//                }
//                else -> null
//            }
        }
        else -> null
    }
}