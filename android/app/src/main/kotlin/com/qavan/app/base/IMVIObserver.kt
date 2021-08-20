package com.qavan.app.base

import com.qavan.app.base.mvi.BaseModelViewIntent
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState

@Suppress("KDocMissingDocumentation")
interface IMVIObserver<VS: BaseViewState, OSE: BaseOneShotEvent> {

    /**
     * Вызывается при каждом изменении текущего состояния viewState
     * @param state последний измененный ViewState
     * @see BaseModelViewIntent.viewState
     */
    fun onState(state: VS)

    /**
     * Вызывается при каждом новом oneShotEvent
     * @param event последний полученный ViewState
     * @see BaseModelViewIntent.oneShotEvents
     */
    fun onEvent(event: OSE)
}