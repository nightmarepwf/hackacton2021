package com.qavan.app.ui.screens.create

import com.qavan.app.base.mvi.UiEffect
import com.qavan.app.base.mvi.UiEvent
import com.qavan.app.base.mvi.UiState

class CreateContract {

    sealed class Event: UiEvent {
        data class SetTitle(val title: String): Event()
        data class SetDescription(val description: String): Event()
        data class SetDate(val time: Long): Event()
    }

    data class State(
        val state: CreateState,
    ): UiState

    sealed class CreateState {
        object Idle: CreateState()
    }

    sealed class Effect: UiEffect {
    }

}