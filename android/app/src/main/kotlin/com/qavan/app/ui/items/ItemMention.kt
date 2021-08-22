package com.qavan.app.ui.items

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qavan.app.R
import com.qavan.app.compose.DefaultShape
import com.qavan.app.compose.text.AppTextSubtitle
import com.qavan.app.data.model.Mention

@Preview
@Composable
fun ItemMention(
    modifier: Modifier = Modifier,
    mention: Mention = Mention(),
    onRemoveIconClick: (Mention) -> Unit = {},
) {
    Row(
        modifier = modifier
            .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(50))
            .clip(DefaultShape)
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        AppTextSubtitle(
            modifier = Modifier.padding(start = 6.dp, bottom = 2.dp),
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight(1000))) {
                    append("@")
                }
                append(mention.name)
            },
            color = MaterialTheme.colors.secondary,
        )
        Icon(
            modifier = Modifier
                .rotate(45f)
                .size(23.dp)
                .clip(RoundedCornerShape(50))
                .clickable {
                    onRemoveIconClick(mention)
                },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_add_circle_outline_24),
            contentDescription = null,
            tint = MaterialTheme.colors.secondary,
        )
    }
}