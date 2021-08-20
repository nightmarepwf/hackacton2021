package com.qavan.app.ui.screens.launch

import com.qavan.app.base.mvi.UiEffect
import com.qavan.app.base.mvi.UiEvent
import com.qavan.app.base.mvi.UiState

class LaunchContract {

    sealed class Event: UiEvent

    data class State(
        val state: LaunchState,
    ): UiState

    sealed class LaunchState {
        object Idle: LaunchState()
    }

    sealed class Effect: UiEffect

}