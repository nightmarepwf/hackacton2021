package com.qavan.app.extensions

import com.qavan.app.BuildConfig

inline val isGms: Boolean
    get() = BuildConfig.FLAVOR_services == "gms"

inline fun debug(
    crossinline action: () -> Unit,
) {
    if (BuildConfig.DEBUG) action()
}