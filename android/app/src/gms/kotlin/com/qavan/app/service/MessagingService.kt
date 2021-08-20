package com.qavan.app.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.qavan.app.base.serviceabstractions.AbstractPushProvider
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MessagingService: FirebaseMessagingService() {

    companion object {
        val TAG = MessagingService::class.java.simpleName
    }

    @Inject
    lateinit var pushProvider: AbstractPushProvider<RemoteMessage>

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("New cloud messaging token $token")
        pushProvider.token = token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.d("New message $message")
        pushProvider.onMessage(message)
    }

}