package com.qavan.app.ui.screens.events

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.qavan.app.compose.Loading
import com.qavan.app.compose.buttons.AppButton
import com.qavan.app.compose.buttons.AppButtonAction
import com.qavan.app.compose.text.AppTextH2
import com.qavan.app.data.model.Event
import com.qavan.app.ui.items.ItemEvent


@Composable
fun EventScreen(
    state: EventContract.EventState,
    events: LazyPagingItems<Event>,
    onCreateEventClick: () -> Unit = {},
    onEventClick: (Event) -> Unit = {},
) {
    when(state) {
        EventContract.EventState.Idle -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    AppTextH2(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(),
                        text = "Предстоящие события",
                        textAlign = TextAlign.Start,
                    )
                }
                item {
                    AppButton(
                        modifier = Modifier.padding(),
                        text = "Создать событие",
                        onClick = onCreateEventClick,
                    )
                }
                items(events) { event ->
                    if (event == null) {
                        Loading()
                    }
                    else {
                        ItemEvent(
                            modifier = Modifier.fillMaxWidth(),
                            event = event,
                            onEventClick = onEventClick,
                        )
                    }
                }
                when {
                    events.loadState.refresh is LoadState.Loading || events.loadState.append is LoadState.Loading -> {
                        item {
                            Loading()
                        }
                    }
                }
            }
        }
    }
}