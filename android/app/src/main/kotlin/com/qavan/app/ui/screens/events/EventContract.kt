package com.qavan.app.ui.screens.events

import com.qavan.app.base.mvi.UiEffect
import com.qavan.app.base.mvi.UiEvent
import com.qavan.app.base.mvi.UiState

class EventContract {

    sealed class Event: UiEvent {
        object LoadEvents: Event()
        object Retry : Event()
    }

    data class State(
        val state: EventState,
    ): UiState

    sealed class EventState {
        object Idle: EventState()
        object Loading : EventState()
        object Error : EventState()
    }

    sealed class Effect: UiEffect {
    }

}