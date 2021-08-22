package com.qavan.app.compose.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.Default
import com.qavan.app.compose.DefaultShape
import com.qavan.app.compose.RubikMedium
import com.qavan.app.compose.text.AppTextBody

@Composable
fun AppButtonOutlined(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: AnnotatedString = buildAnnotatedString { append("Button") },
    textFontWeight: FontWeight = FontWeight.SemiBold,
    textFontSize: TextUnit = 14.sp,
    textFontFamily: FontFamily = Default,
    shape: Shape = DefaultShape,
    borderColor: Color = MaterialTheme.colors.primary,
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    textLetterSpacing: TextUnit = (0.5f).sp,
    lineHeight: TextUnit = (25.2f).sp,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {},
) {
    AppButtonOutlinedImpl(
        modifier = modifier,
        enabled = enabled,
        text = text,
        textFontWeight = textFontWeight,
        textFontSize = textFontSize,
        textFontFamily = textFontFamily,
        shape = shape,
        borderColor = borderColor,
        borderWidth = borderWidth,
        contentPadding = contentPadding,
        textLetterSpacing = textLetterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        onClick = onClick,
    )
}

@Composable
fun AppButtonOutlined(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Button",
    textFontWeight: FontWeight = FontWeight.SemiBold,
    textFontSize: TextUnit = 14.sp,
    textFontFamily: FontFamily = Default,
    shape: Shape = DefaultShape,
    borderColor: Color = MaterialTheme.colors.primary,
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    textLetterSpacing: TextUnit = (0.5f).sp,
    lineHeight: TextUnit = (25.2f).sp,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {},
) {
    AppButtonOutlinedImpl(
        modifier = modifier,
        enabled = enabled,
        text = buildAnnotatedString { append(text) },
        textFontWeight = textFontWeight,
        textFontSize = textFontSize,
        textFontFamily = textFontFamily,
        shape = shape,
        borderColor = borderColor,
        borderWidth = borderWidth,
        contentPadding = contentPadding,
        textLetterSpacing = textLetterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        onClick = onClick,
    )
}

@Composable
fun AppButtonOutlinedAction(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: AnnotatedString = buildAnnotatedString { append("Button") },
    textFontWeight: FontWeight = FontWeight.SemiBold,
    textFontSize: TextUnit = 14.sp,
    textFontFamily: FontFamily = RubikMedium,
    shape: Shape = DefaultShape,
    borderColor: Color = MaterialTheme.colors.primary,
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    textLetterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = 21.sp,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {},
) {
    AppButtonOutlinedImpl(
        modifier = modifier,
        enabled = enabled,
        text = text,
        textFontWeight = textFontWeight,
        textFontSize = textFontSize,
        textFontFamily = textFontFamily,
        shape = shape,
        borderColor = borderColor,
        borderWidth = borderWidth,
        contentPadding = contentPadding,
        textLetterSpacing = textLetterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        onClick = onClick,
    )
}

@Composable
fun AppButtonOutlinedAction(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Button",
    textFontWeight: FontWeight = FontWeight.SemiBold,
    textFontSize: TextUnit = 14.sp,
    textFontFamily: FontFamily = RubikMedium,
    shape: Shape = DefaultShape,
    borderColor: Color = MaterialTheme.colors.primary,
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    textLetterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = 21.sp,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {},
) {
    AppButtonOutlinedImpl(
        modifier = modifier,
        enabled = enabled,
        text = buildAnnotatedString { append(text) },
        textFontWeight = textFontWeight,
        textFontSize = textFontSize,
        textFontFamily = textFontFamily,
        shape = shape,
        borderColor = borderColor,
        borderWidth = borderWidth,
        contentPadding = contentPadding,
        textLetterSpacing = textLetterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        onClick = onClick,
    )
}

@Composable
private fun AppButtonOutlinedImpl(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: AnnotatedString,
    textFontWeight: FontWeight,
    textFontSize: TextUnit,
    textFontFamily: FontFamily,
    shape: Shape,
    borderColor: Color,
    borderWidth: Dp,
    contentPadding: PaddingValues,
    textLetterSpacing: TextUnit,
    lineHeight: TextUnit,
    textAlign: TextAlign,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = borderColor,
            disabledContentColor = borderColor,
        ),
        border = BorderStroke(borderWidth, borderColor),
        contentPadding = contentPadding,
        onClick = {
            onClick()
        },
    ) {
        AppTextBody(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            fontWeight = textFontWeight,
            fontSize = textFontSize,
            fontFamily = textFontFamily,
            color = borderColor,
            letterSpacing = textLetterSpacing,
            lineHeight = lineHeight,
            textAlign = textAlign,
        )
    }
}

@Preview(widthDp = 373)
@Composable
private fun AppButtonsOutlinedPreview() {
    AppTheme(true) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppButtonOutlined(
                modifier = Modifier.fillMaxWidth(),
                text = "Button",
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppButtonOutlinedAction(
                modifier = Modifier.wrapContentWidth(),
                text = "Act",
            )
        }
    }
}