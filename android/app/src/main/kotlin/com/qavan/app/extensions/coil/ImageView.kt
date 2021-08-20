package com.qavan.app.extensions.coil

import android.net.Uri
import android.widget.ImageView
import coil.request.ImageRequest
import coil.size.Precision

inline fun ImageView.request(
    url: Uri,
    allowRgb565: Boolean = true,
    crossinline action: ImageRequest.Builder.() -> Unit = {},
): ImageRequest {
    val memoryKey = url.toString()
    return ImageRequest
        .Builder(context)
        .data(url)
        .memoryCacheKey(memoryKey)
        .allowRgb565(allowRgb565)
        .precision(Precision.EXACT)
        .target(this).apply {
            action(this)
         }
        .build()
}