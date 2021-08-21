package com.qavan.app.extensions.android

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment

inline fun <reified I, reified O> Fragment.registerForActivityResult(
    contract: ActivityResultContract<I, O>,
    crossinline onSuccess: (O) -> Unit,
    crossinline onFailure: () -> Unit = {},
): ActivityResultLauncher<I> {
    return registerForActivityResult(contract) { output: O? ->
        when(output) {
            null -> onFailure()
            else -> onSuccess(output)
        }
    }
}

inline fun <reified I, reified O> ComponentActivity.registerForActivityResult(
    contract: ActivityResultContract<I, O>,
    crossinline onSuccess: (O) -> Unit,
    crossinline onFailure: () -> Unit = {},
): ActivityResultLauncher<I> {
    return registerForActivityResult(contract) { output: O? ->
        when(output) {
            null -> onFailure()
            else -> onSuccess(output)
        }
    }
}