package com.qavan.app.data.repository

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class BaseRepository {
    fun <T> delegate(
        defValue: T,
    ): ReadWriteProperty<Any?, T> = object : ReadWriteProperty<Any?, T> {

        var delegateValue: T = defValue

        override fun getValue(thisRef: Any?, property: KProperty<*>) = delegateValue

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            delegateValue = value
        }

    }
}