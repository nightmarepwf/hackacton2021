package com.qavan.app.compose.extensions

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.TypedValue
import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

@Composable
fun rawResource(@RawRes id: Int): ImageBitmap {
    val context = LocalContext.current
    val value = remember { TypedValue() }
    context.resources.getValue(id, value, true)
    return remember(value.string!!.toString()) { rawResource(context.resources, id) }
}

fun rawResource(res: Resources, @RawRes id: Int): ImageBitmap {
    return BitmapFactory.decodeResource(res, id).asImageBitmap()
}