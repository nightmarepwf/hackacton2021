package com.qavan.app.compose

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.qavan.app.R

val Default = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal, FontStyle.Normal),
)

val RubikMedium = FontFamily(
    Font(R.font.rubik_medium, FontWeight.Medium, FontStyle.Normal),
)

val AppTextStyleH1 = TextStyle(
    color = Color.Black,
    fontFamily = Default,
    fontSize = 34.sp,
    fontWeight = FontWeight(700),
    fontStyle = FontStyle.Normal,
    lineHeight = (40.8f).sp,
)

val AppTextStyleH2 = TextStyle(
    color = Color.Black,
    fontFamily = Default,
    fontSize = 22.sp,
    fontWeight = FontWeight(700),
    fontStyle = FontStyle.Normal,
    lineHeight = (26.4f).sp,
)

val AppTextStyleSubheading = TextStyle(
    color = Color.Black,
    fontFamily = Default,
    fontSize = 16.sp,
    fontWeight = FontWeight(700),
    fontStyle = FontStyle.Normal,
    lineHeight = (19.2f).sp,
)

val AppTextStyleBody = TextStyle(
    color = Color.Black,
    fontFamily = Default,
    fontSize = 14.sp,
    fontWeight = FontWeight(400),
    fontStyle = FontStyle.Normal,
    lineHeight = (16.8f).sp,
)

val AppTextStyleCaption = TextStyle(
    color = Color.Black,
    fontFamily = Default,
    fontSize = 12.sp,
    fontWeight = FontWeight(400),
    fontStyle = FontStyle.Normal,
    lineHeight = (14.4f).sp,
)

// Set of Material typography styles to start with
val typography = Typography(
    defaultFontFamily = Default,
//    h1 = AppTextStyleH1,
//    h2 = AppTextStyleH2,
//    subtitle1 = AppTextStyleSubheading,
//    body1 = AppTextStyleBody,
//    caption = AppTextStyleCaption,
)