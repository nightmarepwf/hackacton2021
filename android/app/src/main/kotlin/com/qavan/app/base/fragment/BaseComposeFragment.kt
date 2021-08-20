package com.qavan.app.base.fragment

import androidx.lifecycle.lifecycleScope
import com.qavan.app.BuildConfig
import com.qavan.app.base.mvi.BaseFragmentMVI
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import kotlinx.coroutines.flow.collect
import timber.log.Timber

abstract class BaseComposeFragment<VS: BaseViewState, OSE: BaseOneShotEvent, FMVI: BaseFragmentMVI<VS, OSE>>:
    BaseFragment<VS, OSE, FMVI>() {

    /**
     * Высота статус бара устройства
     */
    override val statusBarHeight: Int by lazy {
        (mvi.statusBarHeight / requireContext().resources.displayMetrics.scaledDensity).toInt()
    }

    override fun onState(state: VS) = Unit
    override fun observeViewStates() = Unit

    override fun observeOneShotEvents() {
        lifecycleScope.launchWhenStarted {
            mvi.oneShotEvents.collect { oneShotEvent ->
                onEvent(oneShotEvent)
                if (BuildConfig.DEBUG)
                    Timber.w("EVENT ${oneShotEvent::class.java.simpleName}")
            }
        }
    }

}