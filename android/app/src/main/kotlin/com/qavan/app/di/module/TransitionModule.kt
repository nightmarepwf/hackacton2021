package com.qavan.app.di.module

import android.content.Context
import android.transition.Transition
import android.transition.TransitionInflater
import com.qavan.app.di.qualifiers.SharedTransition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
object TransitionModule {

    @Provides
    @SharedTransition
    fun provideSharedTransition(
        context: Context
    ): Transition {
        return TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
            duration = 350
        }
    }

}
