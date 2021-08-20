package com.qavan.app.compose.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.Default
import com.qavan.app.compose.DefaultShape
import com.qavan.app.compose.RubikMedium
import com.qavan.app.compose.text.AppTextBody

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: AnnotatedString = buildAnnotatedString { append("Button") },
    textFontSize: TextUnit = 14.sp,
    textFontFamily: FontFamily = Default,
    shape: Shape = DefaultShape,
    buttonColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
    textLetterSpacing: TextUnit = (0.5f).sp,
    lineHeight: TextUnit = (25.2f).sp,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {},
) {
    AppButtonImpl(
        modifier = modifier,
        enabled = enabled,
        text = text,
        textFontSize = textFontSize,
        textFontFamily = textFontFamily,
        shape = shape,
        buttonColor = buttonColor,
        textColor = textColor,
        border = border,
        contentPadding = contentPadding,
        textLetterSpacing = textLetterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        onClick = onClick,
    )
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Button",
    textFontSize: TextUnit = 14.sp,
    textFontFamily: FontFamily = Default,
    shape: Shape = DefaultShape,
    buttonColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
    textLetterSpacing: TextUnit = (0.5f).sp,
    lineHeight: TextUnit = (25.2f).sp,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {},
) {
    AppButtonImpl(
        modifier = modifier,
        enabled = enabled,
        text = buildAnnotatedString { append(text) },
        shape = shape,
        buttonColor = buttonColor,
        textColor = textColor,
        textFontSize = textFontSize,
        textFontFamily = textFontFamily,
        border = border,
        contentPadding = contentPadding,
        textLetterSpacing = textLetterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        onClick = onClick,
    )
}

@Composable
fun AppButtonAction(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: AnnotatedString = buildAnnotatedString { append("Button") },
    textFontSize: TextUnit = 14.sp,
    textFontFamily: FontFamily = RubikMedium,
    shape: Shape = DefaultShape,
    buttonColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    textLetterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = 21.sp,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {},
) {
    AppButtonImpl(
        modifier = modifier,
        enabled = enabled,
        text = text,
        shape = shape,
        buttonColor = buttonColor,
        textColor = textColor,
        textFontSize = textFontSize,
        textFontFamily = textFontFamily,
        border = border,
        contentPadding = contentPadding,
        textLetterSpacing = textLetterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        onClick = onClick,
    )
}

@Composable
fun AppButtonAction(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Button",
    textFontSize: TextUnit = 14.sp,
    textFontFamily: FontFamily = RubikMedium,
    shape: Shape = DefaultShape,
    buttonColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    textLetterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = 21.sp,
    textAlign: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {},
) {
    AppButtonImpl(
        modifier = modifier,
        enabled = enabled,
        text = buildAnnotatedString { append(text) },
        shape = shape,
        buttonColor = buttonColor,
        textColor = textColor,
        textFontSize = textFontSize,
        textFontFamily = textFontFamily,
        border = border,
        contentPadding = contentPadding,
        textLetterSpacing = textLetterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        onClick = onClick,
    )
}

@Composable
private fun AppButtonImpl(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: AnnotatedString,
    shape: Shape,
    buttonColor: Color,
    textColor: Color,
    textFontSize: TextUnit,
    textFontFamily: FontFamily,
    border: BorderStroke? = null,
    contentPadding: PaddingValues,
    textLetterSpacing: TextUnit,
    lineHeight: TextUnit,
    textAlign: TextAlign,
    onClick: () -> Unit = {},
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = buttonColor,
            contentColor = textColor,
            disabledContentColor = textColor,
        ),
        border = border,
        contentPadding = contentPadding,
        onClick = {
            onClick()
        },
    ) {
        AppTextBody(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            fontSize = textFontSize,
            fontFamily = textFontFamily,
            color = textColor,
            letterSpacing = textLetterSpacing,
            lineHeight = lineHeight,
            textAlign = textAlign,
        )
    }
}

@Preview(widthDp = 373)
@Composable
fun AppButtonsPreview() {
    AppTheme(true) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Button",
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppButtonAction(
                modifier = Modifier.wrapContentWidth(),
                text = "Act",
            )
        }
    }
}