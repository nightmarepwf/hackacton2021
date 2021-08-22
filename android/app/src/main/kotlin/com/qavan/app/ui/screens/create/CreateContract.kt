package com.qavan.app.ui.screens.create

import com.qavan.app.base.mvi.UiEffect
import com.qavan.app.base.mvi.UiEvent
import com.qavan.app.base.mvi.UiState
import com.qavan.app.data.model.TagX

class CreateContract {

    sealed class Event: UiEvent {
        data class SetTitle(val title: String): Event()
        data class SetDescription(val description: String): Event()
        data class SetDate(val time: Long): Event()
        data class AddTag(val tag: TagX): Event()
        data class RemoveTag(val tag: TagX): Event()
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