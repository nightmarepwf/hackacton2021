package com.qavan.app.ui.screens.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.qavan.app.compose.AppTheme
import com.qavan.app.compose.ErrorBox
import com.qavan.app.compose.Loading
import com.qavan.app.compose.LoadingBox
import com.qavan.app.compose.buttons.AppButtonAction
import com.qavan.app.data.model.Event
import com.qavan.app.ui.items.ItemEvent


@Composable
fun EventScreen(
    state: EventContract.EventState,
    events: LazyPagingItems<Event>,
    onCreateEventClick: () -> Unit = {},
    onEventClick: (Event) -> Unit = {},
    onRetryClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        AppButtonAction(
            text = "Создать событие",
            onClick = onCreateEventClick,
        )
        when(state) {
            EventContract.EventState.Idle -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
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
                        events.loadState.append is LoadState.Loading -> {
                            item {
                                Loading()
                            }
                        }
                    }
                }
            }
            EventContract.EventState.Error -> {
                ErrorBox(
                    text = "При загрузке что-то пошло не так",//TODO I18N
                    buttonText = "Попробовать снова",//TODO I18N
                ) {
                    onRetryClick()
                }
            }
            EventContract.EventState.Loading -> {
                LoadingBox()
            }
        }
    }
}

@Preview
@Composable
private fun EventScreenPreview() {
    AppTheme(true) {

    }
}