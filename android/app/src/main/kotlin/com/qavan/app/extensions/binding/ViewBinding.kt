package com.qavan.app.extensions.binding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import timber.log.Timber
import java.lang.reflect.Method
import kotlin.reflect.KClass

inline fun <reified T : ViewBinding> ViewGroup.inflateViewBinding(
    context: Context = this.context,
    attachToRoot: Boolean = true
): T {
    return T::class.inflate(LayoutInflater.from(context), this, attachToRoot)
}

inline fun <reified T : ViewBinding> ViewGroup.inflateItemViewBinding(): T {
    return inflateViewBinding(attachToRoot = false)
}

@PublishedApi
internal val inflateMethodsCache by lazy { mutableMapOf<Class<out ViewBinding>, Method>() }

@PublishedApi
internal inline fun <reified T : ViewBinding> KClass<T>.inflate(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    attachToRoot: Boolean
): T {
    val inflateMethod = java.getInflateMethod()

    @Suppress("UNCHECKED_CAST")
    return if (inflateMethod.parameterTypes.size > 2) {
        inflateMethod.invoke(null, inflater, parent, attachToRoot)
    } else {
        if (!attachToRoot) {
            Timber.d("ViewBinding attachToRoot is always true for ${java.simpleName}.inflate")
        }
        inflateMethod.invoke(null, inflater, parent)
    } as T
}

@PublishedApi
internal inline fun <reified T: ViewBinding> Class<T>.getInflateMethod(): Method {
    return inflateMethodsCache.getOrPut(this) {
        declaredMethods.find { method ->
            val parameterTypes = method.parameterTypes
            method.name == "inflate" &&
                parameterTypes[0] == LayoutInflater::class.java &&
                parameterTypes.getOrNull(1) == ViewGroup::class.java &&
                (parameterTypes.size == 2 || parameterTypes[2] == Boolean::class.javaPrimitiveType)
        }
            ?: error("Method ${this.simpleName}.inflate(LayoutInflater, ViewGroup[, boolean]) not found.")
    }
}