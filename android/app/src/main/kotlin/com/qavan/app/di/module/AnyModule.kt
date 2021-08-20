package com.qavan.app.di.module

import androidx.lifecycle.SavedStateHandle
import com.qavan.app.di.qualifiers.FabManagerQualifier
import com.qavan.app.manager.FabManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AnyModule {

    @Provides
    fun provideSavedStateHandle() = SavedStateHandle()

    @Provides
    @FabManagerQualifier
    fun providerFabManager() = FabManager()

}