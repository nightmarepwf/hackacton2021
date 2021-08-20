package com.qavan.app.extensions.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

@Composable
fun AnnotatedString.Builder.addTextAndStyleForEachUppercaseLiteral(
    string: String,
    style: SpanStyle,
): AnnotatedString.Builder = this.apply {
    var x = length
    append(string)
    val strings = string.split("(?=\\p{Upper})".toRegex()).filterNot {  it.isEmpty() }
    for (i in strings.indices) {
        addStyle(style, x, x + 1)
        x += strings[i].length
    }
}