package com.qavan.app.base.utils

import com.huawei.agconnect.crash.AGConnectCrash

class CrashReportingTree(
    private val agConnectCrash: AGConnectCrash,
) : BaseCrashReportingTree() {

    override fun setCustomKey(key: String, value: String) {
        agConnectCrash.setCustomKey(key, value)
    }

    override fun recordException(throwable: Throwable?, message: String) {
        when(throwable){
            null -> agConnectCrash.recordException(MessageException(message))
            else -> agConnectCrash.recordException(throwable)
        }
    }

}