package com.qavan.app.manager

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import java.lang.ref.WeakReference

class ToastManager constructor(
    private val context: Context
) {

    var currentToast: WeakReference<Toast?> = WeakReference(null)

    fun toast(@StringRes toastText: Int) {
        currentToast.get()?.cancel()
        currentToast = WeakReference(Toast.makeText(context, toastText, Toast.LENGTH_SHORT))
        currentToast.get()?.show()
    }

    fun longToast(@StringRes toastText: Int) {
        currentToast.get()?.cancel()
        currentToast = WeakReference(Toast.makeText(context, toastText, Toast.LENGTH_LONG))
        currentToast.get()?.show()
    }

    fun toast(toastText: String) {
        currentToast.get()?.cancel()
        currentToast = WeakReference(Toast.makeText(context, toastText, Toast.LENGTH_SHORT))
        currentToast.get()?.show()
    }

    fun longToast(toastText: String) {
        currentToast.get()?.cancel()
        currentToast = WeakReference(Toast.makeText(context, toastText, Toast.LENGTH_LONG))
        currentToast.get()?.show()
    }

}