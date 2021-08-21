package com.qavan.app.ui.screens.bloggers

import com.qavan.app.base.mvi.UiEffect
import com.qavan.app.base.mvi.UiEvent
import com.qavan.app.base.mvi.UiState
import com.qavan.app.data.model.Blogger

class BloggersContract {

    sealed class Event: UiEvent {
        data class SelectBlogger(val blogger: Blogger): Event()
        data class DeselectBlogger(val blogger: Blogger): Event()
    }

    data class State(
        val state: BloggersState,
    ): UiState

    sealed class BloggersState {
        object Idle: BloggersState()
        object Loading: BloggersState()
    }

    sealed class Effect: UiEffect {
    }

}