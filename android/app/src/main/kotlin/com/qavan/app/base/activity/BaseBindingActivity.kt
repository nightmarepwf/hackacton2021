package com.qavan.app.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.qavan.app.BuildConfig
import com.qavan.app.base.IMVIObserver
import com.qavan.app.base.mvi.BaseModelViewIntent
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 * Базовая активити с возможностью биндинга ее вью
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseBindingActivity<T: ViewDataBinding, VS: BaseViewState, OSE: BaseOneShotEvent, MVI: BaseModelViewIntent<VS, OSE>>(
    private val layoutId: Int
): AppCompatActivity(), IMVIObserver<VS, OSE> {

    abstract val mvi: MVI

    lateinit var binding: T private set

    /**
     * Устанавливаем контекст в mvi и начинаем наблюдение за state'ами и event'ами
     * @see AppCompatActivity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            Timber.e(e)
        }
        bind()
        lifecycleScope.launchWhenStarted{
            mvi.viewStates().collect { viewState ->
                if (viewState != null) {
                    onState(viewState)
                    if (BuildConfig.DEBUG)
                        Timber.w("STATE ${viewState::class.java.simpleName}")
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            mvi.oneShotEvents.collect { oneShotEvent ->
                onEvent(oneShotEvent)
                if (BuildConfig.DEBUG)
                    Timber.w("EVENT ${oneShotEvent::class.java.simpleName}")
            }
        }
    }

    /**
     * Вызывается сразу после развертывания вьюхи и установки binding'a
     */
    open fun onExecutePendingBindings() = Unit


    /**
     * Функция биндинга
     */
    private fun bind() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        onExecutePendingBindings()
        binding.executePendingBindings()
    }

}