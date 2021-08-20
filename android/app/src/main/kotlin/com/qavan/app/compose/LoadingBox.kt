package com.qavan.app.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("ModifierParameter")
@Composable
fun LoadingBox(
    modifier: Modifier = Modifier.fillMaxSize(),
    loadingModifier: Modifier = Modifier,
    color: Color = Color(0xFF0880AE),
    strokeWidth: Dp = 4.dp,
) {
    Box(modifier = modifier){
        Loading(
            modifier = loadingModifier.align(Alignment.Center),
            color = color,
            strokeWidth = strokeWidth,
        )
    }
}