package com.qavan.app.di.module

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.WorkerFactory
import coil.ImageLoader
import coil.request.CachePolicy
import coil.util.CoilUtils
import coil.util.DebugLogger
import com.qavan.app.BuildConfig
import com.qavan.app.R
import com.qavan.app.base.BaseApplication
import com.qavan.app.di.factory.InjectableWorkerFactory
import com.qavan.app.manager.ImageSnackbarManager
import com.qavan.app.manager.ResourceManager
import com.qavan.app.manager.ResourceManagerImpl
import com.qavan.app.manager.ToastManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {

    @Provides
    @Singleton
    fun provideContext(): Context = BaseApplication.INSTANCE.applicationContext

    @Provides
    @Singleton
    fun provideResourceManager(context: Context): ResourceManager = ResourceManagerImpl(context)

    @Provides
    @Singleton
    fun provideToaster(context: Context): ToastManager = ToastManager(context)

    @Provides
    @Singleton
    fun provideImageSnackbarManager(resourceManager: ResourceManager): ImageSnackbarManager = ImageSnackbarManager(resourceManager)

    @Provides
    @Singleton
    fun provideCoilLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(200)
            .placeholder(R.drawable.ic_placeholder)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .okHttpClient {
                val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }
                OkHttpClient()
                    .newBuilder()
                    .cache(CoilUtils.createDefaultCache(context))
                    .dispatcher(dispatcher)
                    .build()
            }
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger(Log.VERBOSE))
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("${BuildConfig.PROJECT}.PREF", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideInjectableWorkerFactory(): WorkerFactory {
        return InjectableWorkerFactory()
    }

}