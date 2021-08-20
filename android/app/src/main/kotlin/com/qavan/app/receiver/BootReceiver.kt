package com.qavan.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BootReceiver: BroadcastReceiver() {

    companion object {
        private const val ACTION_BOOT_COMPLETED = "android.intent.action.ACTION_BOOT_COMPLETED"
        private const val QUICKBOOT_POWERON = "android.intent.action.QUICKBOOT_POWERON"
        private const val QUICKBOOT_POWERON_HTC = "com.htc.intent.action.QUICKBOOT_POWERON"
    }

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        when (intent.action) {
            ACTION_BOOT_COMPLETED,
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_LOCKED_BOOT_COMPLETED,
            QUICKBOOT_POWERON,
            QUICKBOOT_POWERON_HTC -> Unit
        }
    }

}