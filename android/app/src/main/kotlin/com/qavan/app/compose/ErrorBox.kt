package com.qavan.app.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qavan.app.compose.buttons.AppButtonAction
import com.qavan.app.compose.text.AppTextSubtitle

@SuppressLint("ModifierParameter")
@Composable
fun ErrorBox(
    modifier: Modifier = Modifier.fillMaxSize(),
    text: String = "Text",
    textColor: Color = MaterialTheme.colors.onSurface,
    buttonText: String = "Button text",
    onButtonClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.background(MaterialTheme.colors.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            AppTextSubtitle(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppButtonAction(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = buttonText,
                onClick = onButtonClick,
            )
        }
    }
}

@Preview(widthDp = 343)
@Composable
private fun ErrorBoxPreview() {
    AppTheme(true) {
        ErrorBox()
    }
}