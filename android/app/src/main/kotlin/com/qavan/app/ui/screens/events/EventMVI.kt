package com.qavan.app.ui.screens.events

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.qavan.app.base.mvi.MVI
import com.qavan.app.data.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventMVI @Inject constructor(
    private val eventsRepository: EventsRepository,
): MVI<EventContract.Event, EventContract.State, EventContract.Effect>() {

    val events by lazy {
        Pager(PagingConfig(pageSize = 10)) {
            eventsRepository.source
        }.flow.cachedIn(viewModelScope)
    }

    override fun createInitialState(): EventContract.State {
        return EventContract.State(
            EventContract.EventState.Idle
        )
    }

    override fun handleEvent(event: EventContract.Event) {
        when(event) {
            is EventContract.Event.LoadEvents -> {

            }
            EventContract.Event.Retry -> {

            }
        }
    }

}