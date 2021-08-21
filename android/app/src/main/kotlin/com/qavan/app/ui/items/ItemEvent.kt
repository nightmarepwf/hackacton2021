package com.qavan.app.ui.items

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.text.AppTextBody
import com.qavan.app.compose.text.AppTextSubtitle
import com.qavan.app.data.model.Event

@Composable
fun ItemEvent(
    modifier: Modifier = Modifier,
    event: Event,
    onEventClick: (Event) -> Unit = {},
) {
    Column(
        modifier = modifier
            .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onEventClick(event)
            }
            .padding(8.dp),
    ) {
        AppTextSubtitle(
            text = event.title,
        )
        AppTextBody(
            text = event.event_description,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
        AppTextBody(
            text = event.event_date_formatted,
        )
        AppTextBody(
            text = "${event.event_participant_count} участника(ов)",
        )
    }
}

@Preview(widthDp = 340)
@Composable
private fun ItemEventPreview() {
    AppTheme(true) {
        ItemEvent(event = Event())
    }
}