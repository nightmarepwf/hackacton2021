package com.qavan.app.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val shapes = Shapes(
    small = RoundedCornerShape(1.dp),
    medium = RoundedCornerShape(2.dp),
    large = RoundedCornerShape(4.dp)
)

val DefaultShape = RoundedCornerShape(5.dp)