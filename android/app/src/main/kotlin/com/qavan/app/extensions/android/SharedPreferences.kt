package com.qavan.app.extensions.android

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
fun <T> SharedPreferences.delegate(
    key: String,
    defValue: T,
): ReadWriteProperty<Any?, T> = object : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when(defValue) {
            is Boolean -> (getBoolean(key, defValue) as T?) ?: defValue
            is Int -> (getInt(key, defValue) as T?) ?: defValue
            is Float -> (getFloat(key, defValue) as T?) ?: defValue
            is Long -> (getLong(key, defValue) as T?) ?: defValue
            is String -> (getString(key, defValue) as T?) ?: defValue
            else -> throw NotImplementedError("${defValue!!::class} $defValue")
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                else -> throw NotImplementedError("${defValue!!::class} $value")
            }
            apply()
        }
    }

}