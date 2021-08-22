package com.qavan.app.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.checkbox.AppCheckBox
import com.qavan.app.compose.text.AppTextBody
import com.qavan.app.compose.text.AppTextH2
import com.qavan.app.compose.text.AppTextSubtitle
import com.qavan.app.data.model.Blogger
import com.qavan.app.data.model.IBlogger

@Composable
fun ItemBlogger(
    modifier: Modifier = Modifier,
    blogger: IBlogger,
    checkable: Boolean = false,
    checked: Boolean = true,
    onBloggerClick: (IBlogger) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onBloggerClick(blogger)
            }
            .padding(8.dp),
    ) {
        if (checkable) {
            AppCheckBox(checked = checked) {
                onBloggerClick(blogger)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        else {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colors.secondary),
            ) {
                AppTextH2(
                    modifier = Modifier.align(Alignment.Center),
                    text = blogger.titleShort.uppercase(),
                    color = Color.White,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            AppTextSubtitle(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight((1000)))) { append("ФИО: ") }
                    append(blogger.title)
                },
            )
            AppTextBody(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight((1000)))) { append("Instagram: ") }
                    append(blogger.inst)
                },
            )
            AppTextBody(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight((1000)))) { append("Youtube: ") }
                    append(blogger.yt)
                },
            )
            AppTextBody(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight((1000)))) { append("Email: ") }
                    append(blogger.mail)
                },
            )
        }
    }
}

@Preview(widthDp = 340)
@Composable
private fun ItemEventPreview() {
    AppTheme(true) {
        val blogger = remember { Blogger() }
        var checked by remember { mutableStateOf(false) }
        ItemBlogger(
            blogger = blogger,
            checked = checked,
        ) {
            checked = !checked
        }
    }
}

@Preview(widthDp = 340)
@Composable
private fun ItemEventPreview2() {
    AppTheme(true) {
        val blogger = remember { Blogger() }
        var checked by remember { mutableStateOf(false) }
        ItemBlogger(
            blogger = blogger,
            checkable = true,
            checked = checked,
        ) {
            checked = !checked
        }
    }
}