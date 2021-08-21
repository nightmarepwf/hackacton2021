package com.qavan.app.base

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkerFactory
import com.qavan.app.BuildConfig
import com.qavan.app.data.repository.UserRepository
import com.qavan.app.data.source.local.PushDataSource
import dagger.Lazy
import timber.log.Timber


abstract class BaseApplication : Application(), Configuration.Provider {

    companion object {
        lateinit var INSTANCE: BaseApplication
    }

    abstract val workerFactory: Lazy<WorkerFactory>
    abstract val userRepository: Lazy<UserRepository>
    abstract val tokenDataSource: Lazy<PushDataSource>
    abstract val debugTimberTree: Timber.Tree
    abstract val releaseTimberTree: Timber.Tree

    override fun onCreate() {
        INSTANCE = this
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(debugTimberTree)
        else
            Timber.plant(releaseTimberTree)

    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().apply {
            if (BuildConfig.DEBUG) {
                setMinimumLoggingLevel(android.util.Log.DEBUG)
            }
            setWorkerFactory(workerFactory.get())
        }.build()
    }

}