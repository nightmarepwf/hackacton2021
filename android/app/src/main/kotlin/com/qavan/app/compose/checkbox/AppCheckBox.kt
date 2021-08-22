package com.qavan.app.compose.checkbox

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qavan.app.R
import com.qavan.app.compose.AppTheme
import com.qavan.app.extensions.compose.alpha50
import com.qavan.app.extensions.compose.disabled

@Composable
fun AppCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(50),
    backgroundColor: Color = MaterialTheme.colors.secondary.alpha50,
    indicatorColor: Color = MaterialTheme.colors.primary,
    @DrawableRes iconRes: Int = R.drawable.ic_checked,
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    var isChecked by remember { mutableStateOf(checked) }
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(shape)
            .background(
                when(checked) {
                    true -> when(enabled) {
                        true -> backgroundColor
                        else -> backgroundColor.disabled
                    }
                    else -> when(enabled) {
                        true -> backgroundColor
                        else -> backgroundColor.disabled
                    }
                }
            )
            .clickable(
                enabled = enabled,
            ) {
                val checkedValueToSet = !isChecked
                isChecked = checkedValueToSet
                onCheckedChange?.invoke(isChecked)
            }
            .padding(start = 0.dp, end = 0.dp, top = 3.dp, bottom = 0.dp),
    ) {
        Icon(
            modifier = Modifier
                .size(14.dp)
                .align(Alignment.Center),
            imageVector = ImageVector.vectorResource(id = iconRes),
            tint = when(checked) {
                 true -> when(enabled) {
                    true -> indicatorColor
                    else -> indicatorColor.disabled
                }
                else -> when(enabled) {
                    true -> Color.Transparent
                    else -> Color.Transparent
                }
            },
            contentDescription = null,
        )
    }
}

@Preview(widthDp = 120)
@Composable
fun AppButtonsPreview() {
    AppTheme(true) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppCheckBox(checked = false)
            Spacer(modifier = Modifier.height(8.dp))
            AppCheckBox(checked = true)
        }
    }
}