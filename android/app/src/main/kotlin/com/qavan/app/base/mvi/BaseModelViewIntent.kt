package com.qavan.app.base.mvi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.qavan.app.BuildConfig
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import com.qavan.app.extensions.kotlin.launchOnUI
import com.qavan.app.manager.ToastManager
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

@Suppress("MemberVisibilityCanBePrivate", "unused", "PropertyName")
abstract class BaseModelViewIntent<VS: BaseViewState, OSE: BaseOneShotEvent>: ViewModel() {

    abstract val state: SavedStateHandle
    abstract val toastManager: ToastManager
    protected open val coroutineExceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.d("Coroutine Exception on $coroutineContext")
            Timber.e(throwable)
            if (BuildConfig.DEBUG) {
                launchOnUI {
                    toast("${throwable.message}")
                }
            }
            onError(throwable)
        }
    }

    val _viewState: MutableStateFlow<VS?> by lazy { MutableStateFlow(null) }
    fun viewStates(): StateFlow<VS?> = _viewState
    protected var viewState: VS
        get() = _viewState.value
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            if (_viewState.value == value) {
                _viewState.value = null
            }
            _viewState.value = value
        }

    val _oneShotEvents: Channel<OSE> by lazy { Channel(Channel.BUFFERED) }
    val oneShotEvents: Flow<OSE> by lazy { _oneShotEvents.receiveAsFlow() }

    open fun onError(throwable: Throwable)  {
        Timber.e(throwable)
    }

    //region viewModelJob

    protected val viewModelScope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.IO + coroutineExceptionHandler) }

    fun onViewModelScope(
        action: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            action()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    //endregion

    //region toast
    fun toast(payload: CharSequence?) = toastManager.toast(payload.toString())
    fun longtoast(payload: CharSequence?) = toastManager.longToast(payload.toString())
    fun toast(payload: String?) = toastManager.toast(payload.toString())
    fun longtoast(payload: String?) = toastManager.longToast(payload.toString())
    fun toast(payload: Int) = toastManager.toast(payload)
    fun longtoast(payload: Int) = toastManager.longToast(payload)
    //endregion

    //region load
    private var isLoadedOnce: Boolean = false

    fun load(
        action: () -> Unit
    ) {
        if (!isLoadedOnce) {
            isLoadedOnce = true
            action()
        }
    }
    //endregion

    internal inline fun CoroutineScope.onUI(
        crossinline action: suspend CoroutineScope.() -> Unit
    ) {
        launch(viewModelScope.coroutineContext + coroutineExceptionHandler + Dispatchers.Main) {
            this.action()
        }
    }

    internal inline fun CoroutineScope.onIO(
        crossinline action: suspend CoroutineScope.() -> Unit
    ) {
        launch(viewModelScope.coroutineContext + coroutineExceptionHandler + Dispatchers.IO) {
            this.action()
        }
    }

    fun shotEvent(shot: OSE) {
        viewModelScope.launch {
            _oneShotEvents.send(shot)
        }
    }
    //endregion
}