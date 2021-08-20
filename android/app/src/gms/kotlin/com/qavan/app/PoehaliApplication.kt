package com.qavan.app

import androidx.work.WorkerFactory
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import com.qavan.app.base.BaseApplication
import com.qavan.app.base.utils.CrashReportingTree
import com.qavan.app.data.repository.UserRepository
import com.qavan.app.data.source.local.PushDataSource
import com.qavan.app.extensions.EMPTY
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class PoehaliApplication: BaseApplication() {

    @Inject
    override lateinit var workerFactory: Lazy<WorkerFactory>

    @Inject
    override lateinit var userRepository: Lazy<UserRepository>

    @Inject
    override lateinit var tokenDataSource: Lazy<PushDataSource>

    override val debugTimberTree: Timber.Tree by lazy { Timber.DebugTree() }

    @Inject
    lateinit var crashlytics: FirebaseCrashlytics

    override val releaseTimberTree: Timber.Tree by lazy { CrashReportingTree(crashlytics) }

    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            when(it.isSuccessful) {
                true -> {
                    val token = it.result
                    Timber.d("Cloud messaging token $token")
                    tokenDataSource.get().currentToken = it.result ?: String.EMPTY
                }
                else -> {
                    Timber.e(it.exception)
                }
            }
        }
    }
}