package com.qavan.app.extensions.android

import androidx.databinding.BaseObservable
import androidx.databinding.Observable


inline fun <reified T: BaseObservable> BaseObservable.onChange(
    crossinline action: (T) -> Unit,
) {
    addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        @Suppress("UNCHECKED_CAST")
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            sender?.let {
               action(it as T)
            }
        }
    })
}