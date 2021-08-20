package com.qavan.app.compose.text

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.qavan.app.compose.Default

@Composable
fun AppTextBody(
    modifier: Modifier = Modifier,
    text: AnnotatedString = buildAnnotatedString { append("AppTextBody") },
    color: Color = MaterialTheme.colors.onSurface,
    fontFamily: FontFamily? = Default,
    fontWeight: FontWeight = FontWeight(400),
    fontStyle: FontStyle = FontStyle.Normal,
    fontSize: TextUnit = 14.sp,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = (16.8f).sp,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
    )
}

@Composable
fun AppTextBody(
    modifier: Modifier = Modifier,
    text: String = "AppTextBody",
    color: Color = MaterialTheme.colors.onSurface,
    fontFamily: FontFamily? = Default,
    fontWeight: FontWeight = FontWeight(400),
    fontStyle: FontStyle = FontStyle.Normal,
    fontSize: TextUnit = 14.sp,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = (16.8f).sp,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString { append(text) },
        color = color,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
    )
}