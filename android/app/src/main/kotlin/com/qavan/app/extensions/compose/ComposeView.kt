package com.qavan.app.extensions.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.qavan.app.compose.AppTheme

inline fun ComposeView.appContent(
    crossinline content: @Composable () -> Unit
) {
    setContent {
        AppTheme {
            content()
        }
    }
}