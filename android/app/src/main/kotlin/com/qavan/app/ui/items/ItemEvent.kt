package com.qavan.app.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.DefaultShape
import com.qavan.app.compose.text.AppTextBody
import com.qavan.app.compose.text.AppTextSubtitle
import com.qavan.app.data.model.Event
import com.qavan.app.extensions.compose.alpha50

@Composable
fun ItemEvent(
    modifier: Modifier = Modifier,
    event: Event,
    onEventClick: (Event) -> Unit = {},
) {
    Box(modifier = Modifier
        .border(1.dp, MaterialTheme.colors.primary, DefaultShape)
        .clip(DefaultShape)
        .clickable {
            onEventClick(event)
        }
        .fillMaxWidth()
        .aspectRatio(1.68f))
    {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = rememberImagePainter(event.event_image) {
                crossfade(true)
            },
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column(
            modifier = modifier
                .background(Color.White.alpha50)
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            AppTextSubtitle(
                text = event.title,
            )
            AppTextBody(
                text = event.event_description,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
            )
            AppTextBody(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight(1000))) { append("Дата: ") }
                    append(event.event_date_formatted)
                },
            )
            AppTextBody(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight(1000))) { append("Участников: ") }
                    append(event.event_participant_count.toString())
                },
            )
        }
    }
}

@Preview(widthDp = 340)
@Composable
private fun ItemEventPreview() {
    AppTheme(true) {
        ItemEvent(event = Event())
    }
}