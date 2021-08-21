package com.qavan.app.ui.items

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.checkbox.AppCheckBox
import com.qavan.app.compose.text.AppTextBody
import com.qavan.app.compose.text.AppTextSubtitle
import com.qavan.app.data.model.Blogger

@Composable
fun ItemBlogger(
    modifier: Modifier = Modifier,
    blogger: Blogger,
    checked: Boolean = true,
    onBloggerClick: (Blogger) -> Unit = {},
) {
    Column(
        modifier = modifier
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onBloggerClick(blogger)
            }
            .padding(8.dp),
    ) {
        AppCheckBox(checked = checked) {
            onBloggerClick(blogger)
        }
        AppTextSubtitle(
            text = "${blogger.u_soname} ${blogger.u_name} ${blogger.u_otch}",
        )
        AppTextBody(
            text = blogger.instagram ?: "Instagram не указан",
        )
        AppTextBody(
            text = blogger.youtube ?: "Youtube не указан",
        )
        AppTextBody(
            text = blogger.email ?: "Email не указан",
        )
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