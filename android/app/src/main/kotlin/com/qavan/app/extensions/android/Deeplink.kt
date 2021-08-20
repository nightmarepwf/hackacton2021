package com.qavan.app.extensions.android

import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.navigation.NavDeepLinkBuilder
import com.qavan.app.base.activity.BaseBindingActivity


inline fun <reified T: BaseBindingActivity<*, *, *, *>> Context.deepLinkTo(
    @NavigationRes navGraph: Int,
    @IdRes destinationId: Int,
    args: Bundle? = null,
): PendingIntent {
    return NavDeepLinkBuilder(this)
        .setGraph(navGraph)
        .setDestination(destinationId)
        .setArguments(args)
        .setComponentName(T::class.java)
        .createPendingIntent()
}