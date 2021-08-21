package com.qavan.app.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FabManager {

    private val state: Channel<Boolean> by lazy { Channel(5) }
    val extendFab by lazy { state.receiveAsFlow() }

    fun send(scope: CoroutineScope, boolean: Boolean) {
        scope.launch {
            state.send(boolean)
        }
    }
}