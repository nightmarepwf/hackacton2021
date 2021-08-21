package com.qavan.app.extensions.compose

import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

inline val Color.disabled: Color
    @Composable
    get() = copy(alpha = ContentAlpha.disabled)

inline val Color.alpha50: Color
    @Composable
    get() = copy(alpha = .5f)