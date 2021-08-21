package com.qavan.app.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import com.qavan.app.data.constants.DEFAULT_ASPECT_RATIO

fun BoxScope.answer(): Modifier = Modifier.composed {
    Modifier
        .fillMaxWidth()
        .align(Alignment.Center)
        .background(MaterialTheme.colors.background)
}

fun BoxScope.answerWithRatio(): Modifier = Modifier.composed {
    Modifier
        .fillMaxWidth()
        .aspectRatio(DEFAULT_ASPECT_RATIO)
        .align(Alignment.Center)
        .background(MaterialTheme.colors.background)
}

fun Modifier.questionWithRatio(): Modifier = Modifier.composed {
    this
        .fillMaxWidth()
        .aspectRatio(DEFAULT_ASPECT_RATIO)
        .clip(MaterialTheme.shapes.medium)
}