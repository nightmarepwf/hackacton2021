package com.qavan.app.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.pixels(): Float {
    return with(LocalDensity.current) { this@pixels.toPx() }
}