package com.qavan.app.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import com.qavan.app.BuildConfig
import com.qavan.app.base.mvi.BaseFragmentMVI
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import kotlinx.coroutines.flow.collect
import timber.log.Timber

abstract class BaseMainComposeBottomSheetFragmentDialog<VS: BaseViewState, OSE: BaseOneShotEvent, FMVI: BaseFragmentMVI<VS, OSE>>:
    BaseComposeBottomSheetFragmentDialog<VS, OSE, FMVI>() {

    /**
     * SavedStateHandle для данных
     */
    val savedStateHandle: SavedStateHandle
        get() = mvi.state

    /**
     * Высота статус бара устройства
     */
    protected val statusBarHeight: Int by lazy {
        (mvi.statusBarHeight / requireContext().resources.displayMetrics.scaledDensity).toInt()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.setCanceledOnTouchOutside(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            mvi.viewStates().collect { viewState ->
                if (viewState != null) {
                    onState(viewState)
                    if (BuildConfig.DEBUG)
                        Timber.e(viewState::class.java.simpleName)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            mvi.oneShotEvents.collect { oneShotEvent ->
                onEvent(oneShotEvent)
                if (BuildConfig.DEBUG)
                    Timber.e(oneShotEvent::class.java.simpleName)
            }
        }
    }


}