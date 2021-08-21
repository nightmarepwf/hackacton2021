package com.qavan.app

import androidx.work.WorkerFactory
import com.huawei.agconnect.crash.AGConnectCrash
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.push.HmsMessaging
import com.qavan.app.base.BaseApplication
import com.qavan.app.base.utils.CrashReportingTree
import com.qavan.app.data.repository.UserRepository
import com.qavan.app.data.source.local.PushDataSource
import com.qavan.app.extensions.EMPTY
import com.qavan.app.extensions.kotlin.launchOnIO
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TemplateApplication: BaseApplication() {

    @Inject
    override lateinit var workerFactory: Lazy<WorkerFactory>

    @Inject
    override lateinit var userRepository: Lazy<UserRepository>

    @Inject
    override lateinit var tokenDataSource: Lazy<PushDataSource>

    override val debugTimberTree: Timber.Tree by lazy { Timber.DebugTree() }

    @Inject
    lateinit var agConnectCrash: AGConnectCrash

    override val releaseTimberTree: Timber.Tree by lazy { CrashReportingTree(agConnectCrash) }

    override fun onCreate() {
        super.onCreate()
        launchOnIO {
            val token: String? = runCatching {
                HmsInstanceId.getInstance(applicationContext).getToken(
                    "104518395",
                    HmsMessaging.DEFAULT_TOKEN_SCOPE
                )
            }.getOrNull() ?: String.EMPTY
            Timber.d("Cloud messaging token $token")
            tokenDataSource.get().currentToken = token
        }
    }

}