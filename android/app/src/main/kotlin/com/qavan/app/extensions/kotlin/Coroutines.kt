package com.qavan.app.extensions.kotlin

import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

val supervisorJob by lazy {
    SupervisorJob()
}

val ceh by lazy {
    CoroutineExceptionHandler { _: CoroutineContext, throwable: Throwable ->
        Timber.e(throwable)
    }
}

inline fun launchOnUI(
    crossinline action: suspend CoroutineScope.() -> Unit
) {
    CoroutineScope(ceh + supervisorJob + Dispatchers.Main).launch {
        this.action()
    }
}

inline fun launchOnIO(
    crossinline action: suspend CoroutineScope.() -> Unit
) {
    CoroutineScope(ceh + supervisorJob + Dispatchers.IO).launch {
        this.action()
    }
}

inline fun CoroutineScope.onUI(
    crossinline action: suspend CoroutineScope.() -> Unit
) {
    launch(ceh + supervisorJob + Dispatchers.Main) {
        this.action()
    }
}

inline fun CoroutineScope.onIO(
    crossinline action: suspend CoroutineScope.() -> Unit
) {
    launch(ceh + supervisorJob + Dispatchers.IO) {
        this.action()
    }
}