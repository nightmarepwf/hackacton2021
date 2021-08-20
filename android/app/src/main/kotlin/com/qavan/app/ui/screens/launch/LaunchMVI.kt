package com.qavan.app.ui.screens.launch

import com.qavan.app.base.mvi.MVI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchMVI @Inject constructor(

): MVI<LaunchContract.Event, LaunchContract.State, LaunchContract.Effect>() {

    override fun createInitialState(): LaunchContract.State {
        return LaunchContract.State(
            LaunchContract.LaunchState.Idle
        )
    }

    override fun handleEvent(event: LaunchContract.Event) {
        when(event) {

        }
    }

}