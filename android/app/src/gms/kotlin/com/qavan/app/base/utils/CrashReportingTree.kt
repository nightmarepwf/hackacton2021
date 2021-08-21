package com.qavan.app.base.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashReportingTree(
    private val crashlytics: FirebaseCrashlytics,
) : BaseCrashReportingTree() {

    override fun setCustomKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }

    override fun recordException(throwable: Throwable?, message: String) {
        when(throwable){
            null -> crashlytics.recordException(MessageException(message))
            else -> crashlytics.recordException(throwable)
        }
    }

}