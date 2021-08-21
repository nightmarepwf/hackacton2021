package com.qavan.app.base.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qavan.app.BuildConfig
import com.qavan.app.base.mvi.BaseFragmentMVI
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import kotlinx.coroutines.flow.collect
import timber.log.Timber

abstract class BaseBindingFragment<T: ViewDataBinding, VS: BaseViewState, OSE: BaseOneShotEvent, FMVI: BaseFragmentMVI<VS, OSE>>(
    private val layoutId: Int,
): BaseFragment<VS, OSE, FMVI>() {

    private var _binding: T? = null
    protected val binding
        get() = _binding!!

    /**
     * Высота статусбара устройства
     */
    override val statusBarHeight: Int
        get() = mvi.statusBarHeight

    /**
     * Устанавливаем _binding
     * @see BottomSheetDialogFragment.onCreateView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
            _binding = this
        }.root
    }

    override fun observeViewStates() {
        lifecycleScope.launchWhenStarted {
            mvi.viewStates().collect { viewState ->
                if (viewState != null) {
                    onState(viewState)
                    if (BuildConfig.DEBUG)
                        Timber.w("STATE ${viewState::class.java.simpleName}")
                }
            }
        }
    }

    override fun observeOneShotEvents() {
        lifecycleScope.launchWhenStarted {
            mvi.oneShotEvents.collect { oneShotEvent ->
                onEvent(oneShotEvent)
                if (BuildConfig.DEBUG)
                    Timber.w("EVENT ${oneShotEvent::class.java.simpleName}")
            }
        }
    }

    /**
     * Устанавливаем контекст в mvi и начинаем наблюдение за state'ами и event'ами
     * @see BottomSheetDialogFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onExecuteBind()
        observeViewStates()
        observeOneShotEvents()
    }

    /**
     * Вызываем родительский метод и очищаем память от биндинга этого фрагмента
     */
    override fun onDestroyView() {
        super.onDestroyView()
        Handler(Looper.getMainLooper()).post {
            _binding = null
        }
    }

    /**
     * Вызывается сразу после установки binding'а
     */
    open fun beforeExecutePendingBindings() = Unit

    open fun onExecuteBind() {
        beforeExecutePendingBindings()
        with(binding) {
            lifecycleOwner = requireActivity()
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
        view.layoutParams  = (view.layoutParams as ViewGroup.MarginLayoutParams).apply {
            topMargin = statusBarHeight
        }
    }


}