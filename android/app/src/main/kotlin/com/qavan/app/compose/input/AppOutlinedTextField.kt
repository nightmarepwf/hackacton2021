package com.qavan.app.compose.input

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qavan.app.compose.*
import com.qavan.app.compose.text.AppTextBody
import com.qavan.app.compose.text.AppTextCaption
import com.qavan.app.compose.text.AppTextSubtitle
import com.qavan.app.extensions.compose.alpha50
import com.qavan.app.extensions.compose.disabled
import kotlinx.coroutines.launch

@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textColor: Color = MaterialTheme.colors.primary,
    textStyle: TextStyle = if (value.isBlank()) AppTextStyleBody.copy(color = textColor, fontFamily = RubikMedium) else AppTextStyleBody.copy(color = textColor),
    label: String? = null,
    placeholder: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
//    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    shape: Shape = DefaultShape,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colors.primary,
        disabledTextColor = MaterialTheme.colors.primary.disabled,
        backgroundColor = Color.Transparent,
        cursorColor = MaterialTheme.colors.primary,
//        errorCursorColor = ,
        focusedBorderColor = MaterialTheme.colors.primary,
        unfocusedBorderColor = MaterialTheme.colors.primary,
        disabledBorderColor = MaterialTheme.colors.primary.disabled,
//        errorBorderColor = ,
//        leadingIconColor = ,
//        disabledLeadingIconColor = ,
//        errorLeadingIconColor = ,
        trailingIconColor = MaterialTheme.colors.primary,
        disabledTrailingIconColor = MaterialTheme.colors.primary.disabled,
//        errorTrailingIconColor = ,
        focusedLabelColor = MaterialTheme.colors.primary,
        unfocusedLabelColor = MaterialTheme.colors.primary,
        disabledLabelColor = MaterialTheme.colors.primary.disabled,
//        errorLabelColor = ,
        placeholderColor = MaterialTheme.colors.primary.alpha50,
        disabledPlaceholderColor = MaterialTheme.colors.primary.alpha50.disabled,
    ),
    onFocusChanged: (hasFocus: Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    Box(
        modifier = modifier
    ) {
        var isTextFieldHasFocus by remember { mutableStateOf(false) }
        val topBlankPadding = remember {
            30f - with(density) {
                AppTextStyleCaption.fontSize.value.toDp().value
            }
        }
        val paddingStart = remember {
            Animatable(if (value.isBlank()) 12f else 31f)
        }
        val paddingTop = remember {
            Animatable(if (isTextFieldHasFocus || value.isBlank()) topBlankPadding else 0f)
        }
        val labelBackgroundAlpha = remember {
            Animatable(if (isTextFieldHasFocus) 1f else if (value.isBlank()) 0f else 1f)
        }
        val labelFontSize = remember {
            Animatable(
                when {
                    isTextFieldHasFocus -> 12f
                    else -> when {
                        value.isBlank() -> 14f
                        else -> 12f
                    }
                }
            )
        }
        AppOutlinedTextFieldImpl(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    isTextFieldHasFocus = it.isFocused
                    when {
                        isTextFieldHasFocus -> {
                            scope.launch { paddingTop.animateTo(0f) }
                            scope.launch { paddingStart.animateTo(31f) }
                            scope.launch { labelBackgroundAlpha.animateTo(1f) }
                            scope.launch { labelFontSize.animateTo(12f) }
                        }
                        else -> {
                            if (value.isBlank()) {
                                scope.launch { paddingTop.animateTo(topBlankPadding) }
                                scope.launch { paddingStart.animateTo(12f) }
                                scope.launch { labelBackgroundAlpha.animateTo(0f) }
                                scope.launch { labelFontSize.animateTo(14f) }
                            } else {
                                scope.launch { paddingTop.animateTo(0f) }
                                scope.launch { paddingStart.animateTo(31f) }
                                scope.launch { labelBackgroundAlpha.animateTo(1f) }
                                scope.launch { labelFontSize.animateTo(12f) }
                            }
                        }
                    }
                    onFocusChanged(it.isFocused)
                },
            value = value,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            placeholder = when (placeholder) {
                null -> null
                else -> buildAnnotatedString { append(placeholder) }
            },
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            shape = shape,
            colors = colors,
            onValueChange = onValueChange
        )
        if (label != null) {
            AppTextCaption(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(
                        start = paddingStart.value.dp,
                        end = 0.dp,
                        top = paddingTop.value.dp,
                        bottom = 0.dp
                    )
                    .background(MaterialTheme.colors.surface.copy(alpha = labelBackgroundAlpha.value))
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    )
                    .align(Alignment.TopStart),
                text = label.toString(),
                fontSize = labelFontSize.value.sp,
                color = MaterialTheme.colors.primary,
            )
        }
    }
}

@Composable
private fun AppOutlinedTextFieldImpl(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textColor: Color = MaterialTheme.colors.primary,
    textStyle: TextStyle = when {
        value.isBlank() -> AppTextStyleBody.copy(color = textColor, fontFamily = RubikMedium)
        else -> AppTextStyleBody.copy(color = textColor)
    },
    placeholder: AnnotatedString? = buildAnnotatedString {  },
    trailingIcon: @Composable (() -> Unit)? = null,
//    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    shape: Shape = DefaultShape,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colors.primary,
        disabledTextColor = MaterialTheme.colors.primary.disabled,
        backgroundColor = Color.Transparent,
        cursorColor = MaterialTheme.colors.primary,
//        errorCursorColor = ,
        focusedBorderColor = MaterialTheme.colors.primary,
        unfocusedBorderColor = MaterialTheme.colors.primary,
        disabledBorderColor = MaterialTheme.colors.primary.disabled,
//        errorBorderColor = ,
//        leadingIconColor = ,
//        disabledLeadingIconColor = ,
//        errorLeadingIconColor = ,
        trailingIconColor = MaterialTheme.colors.primary,
        disabledTrailingIconColor = MaterialTheme.colors.primary.disabled,
//        errorTrailingIconColor = ,
        focusedLabelColor = MaterialTheme.colors.primary,
        unfocusedLabelColor = MaterialTheme.colors.primary,
        disabledLabelColor = MaterialTheme.colors.primary.disabled,
//        errorLabelColor = ,
        placeholderColor = MaterialTheme.colors.primary.alpha50,
        disabledPlaceholderColor = MaterialTheme.colors.primary.alpha50.disabled,
    ),
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = {
            Surface {}
        },
        placeholder = {
            if (placeholder != null) {
                AppTextSubtitle(
                    modifier = Modifier.fillMaxWidth(),
                    text = placeholder,
                    color = MaterialTheme.colors.primary.alpha50,
                )
            }
        },
        trailingIcon = trailingIcon,
//        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        shape = shape,
        colors = colors,
        onValueChange = onValueChange,
    )
}

@Preview(widthDp = 120)
@Composable
fun AppButtonsPreview() {
    AppTheme(true) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppOutlinedTextField(value = "") {}
            Spacer(modifier = Modifier.height(8.dp))
            AppOutlinedTextField(value = "Value") {}
        }
    }
}