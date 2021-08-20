package com.qavan.app.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.qavan.app.BuildConfig
import com.qavan.app.base.mvi.BaseFragmentMVI
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import kotlinx.coroutines.flow.collect
import timber.log.Timber

abstract class BaseMainBottomSheetFragmentDialog<T: ViewDataBinding, VS: BaseViewState, OSE: BaseOneShotEvent, FMVI: BaseFragmentMVI<VS, OSE>>(
    layoutId: Int,
): BaseBottomSheetFragmentDialog<T, VS, OSE, FMVI>(layoutId) {

    private val statusBarHeight: Int
        get() = mvi.statusBarHeight

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.setCanceledOnTouchOutside(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onExecuteBind()
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

    /**
     * Вызывается сразу после установки binding'а
     */
    open fun beforeExecutePendingBindings() = Unit

    override fun onExecuteBind() {
        with(binding) {
            lifecycleOwner = requireActivity()
            beforeExecutePendingBindings()
            executePendingBindings()
        }
    }

    /**
     * Установка внутренный отступ сверху высотой стута бара устройства
     * @param view вью, для которой необходимо установить отступ сверху
     */
    fun updateTopPadding(
        view: View,
    ) {
        view.setPadding(
            view.paddingStart,
            statusBarHeight,
            view.paddingRight,
            view.paddingBottom,
        )
    }

    /**
     * Установка margin сверху высотой стута бара устройства
     * @param view вью, для которой необходимо установить отступ сверху
     */
    fun setTopMargin(
        view: View,
    ) {
        view.layoutParams  = (view.layoutParams as LinearLayout.LayoutParams).apply {
            topMargin = statusBarHeight
        }
    }


}