package com.qavan.app.base.utils

import android.util.Log
import timber.log.Timber

abstract class BaseCrashReportingTree : Timber.Tree() {

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "PRIORITY"
        private const val CRASHLYTICS_KEY_TAG = "TAG"
        private const val CRASHLYTICS_KEY_MESSAGE = "MESSAGE"
    }

    abstract fun setCustomKey(key: String, value: String)

    abstract fun recordException(throwable: Throwable?, message: String)

    override fun log(
        priority: Int,
        tag: String?,
        message: String,
        t: Throwable?
    ) {
        when (priority) {
            Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN -> return
        }

        setCustomKey(CRASHLYTICS_KEY_PRIORITY, getStringPriority(priority))
        setCustomKey(CRASHLYTICS_KEY_TAG, getStringTag(tag))
        setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)

        recordException(t, message)
    }

    private fun getStringPriority(
        priority: Int,
    ) = when(priority) {
        Log.ERROR -> "ERROR"
        Log.ASSERT -> "ASSERT"
        else -> "UNSPECIFIED"
    }

    private fun getStringTag(
        tag: String?,
    ) = when(tag) {
        null -> "UNSPECIFIED"
        else -> tag
    }

    class MessageException(message: String): Exception(message)

}