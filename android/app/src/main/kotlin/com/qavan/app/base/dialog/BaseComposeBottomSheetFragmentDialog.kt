package com.qavan.app.base.dialog

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qavan.app.BuildConfig
import com.qavan.app.base.IMVIObserver
import com.qavan.app.base.mvi.BaseModelViewIntent
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import com.qavan.app.extensions.EMPTY
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 * Всплывающий из нижней границы экрана диалоговый фрагмент
 */
@Suppress("unused")
abstract class BaseComposeBottomSheetFragmentDialog<VS: BaseViewState, OSE: BaseOneShotEvent, FMVI: BaseModelViewIntent<VS, OSE>>: BottomSheetDialogFragment(), IMVIObserver<VS, OSE> {

    abstract val mvi: FMVI

    val navController by lazy { findNavController() }

    /**
     * Устанавливаем контекст в mvi и начинаем наблюдение за state'ами и event'ами
     * @see BottomSheetDialogFragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            mvi.viewStates().collect { viewState ->
                if (viewState != null) {
                    onState(viewState)
                }
                if (BuildConfig.DEBUG && viewState != null)
                    Timber.w("STATE ${viewState::class.java.simpleName}")
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
     * Проверка доступности точки навигации из текущей, и при доступности навигация в нее
     *
     * @param actionId точка навигации
     * @param navigationAction функция, вызываемая при доступности точки навигации
     */
    private fun navigateIfAvailable(
        actionId: Int,
        navigationAction: () -> Unit,
    ) {
        if (navController.currentDestination?.getAction(actionId) != null)
            navigationAction()
        else
            Timber.e("Navigation from ${navController.currentDestination?.id} to action with id=$actionId can not be performed")
    }

    /**
     * Проверка доступности точки навигации из текущей, и при доступности навигация в нее
     *
     * @param direction точка навигации
     * @param navigationAction функция, вызываемая при доступности точки навигации
     */
    private fun navigateIfAvailable(
        direction: NavDirections,
        navigationAction: () -> Unit,
    ) {
        navigateIfAvailable(direction.actionId) { navigationAction() }
    }

    /**
     * Navigate with args
     * @param action NavDirections
     */
    fun navigateWithAction(action: NavDirections) {
        navigateIfAvailable(action) {
            navController.navigate(action)
        }
    }

    /**
     * Navigate with args
     * @param resId action/direction id
     */
    fun navigate(resId: Int) {
        navigateIfAvailable(resId) {
            navController.navigate(resId)
        }
    }

    /**
     * Navigate with args
     * @param resId action/direction id
     * @param args Bundle of arguments
     */
    fun navigateWithArgs(
        resId: Int,
        args: Bundle,
    ) {
        navigateIfAvailable(resId) {
            navController.navigate(resId, args)
        }
    }

    /**
     * Navigate direction extras
     * @param direction NavDirections
     * @param extras FragmentNavigator.Extras
     */
    fun navigateDirectionExtras(
        direction: NavDirections,
        extras: FragmentNavigator.Extras,
    ) {
        navigateIfAvailable(direction) {
            navController.navigate(direction, extras)
        }
    }

    //region arg lazy functions
    /**
     * Получаем Lazy<Boolean> из аргументов фрагмента, при отсутствии такого аргумента возвращает
     * false
     * @param argKey ключ аргумента
     */
    infix fun argBoolean(argKey: String): Lazy<Boolean> {
        return lazy {
            when(val args = arguments) {
                null -> false
                else -> args.getBoolean(argKey)
            }
        }
    }

    /**
     * Получаем Lazy<String> из аргументов фрагмента, при отсутствии такого аргумента возвращает
     * пустую строку
     * @param argKey ключ аргумента
     */
    infix fun argString(argKey: String): Lazy<String> {
        return lazy {
            when(val args = arguments) {
                null -> String.EMPTY
                else -> args.getString(argKey)!!
            }
        }
    }

    /**
     * Получаем Lazy<String> из аргументов фрагмента
     * Если такого аргумента нет, то возвращает -1
     * @param arg ключ-значение аргумента, где ключ - ключ для получения аргумента, значение -
     * значение возвращаемое при отсутствии аргумента
     */
    infix fun argStringNullable(arg: Pair<String,String>): Lazy<String> {
        return lazy {
            when(val args = arguments) {
                null -> arg.second
                else -> args.getString(arg.first) ?: arg.second
            }
        }
    }

    /**
     * Получаем Lazy<Array<String>?> из аргументов фрагмента
     * @param argKey ключ аргумента
     */
    infix fun argStringArrayNullable(argKey: String): Lazy<Array<String>?> {
        return lazy {
            when(val args = arguments) {
                null -> null
                else -> args.getStringArray(argKey)
            }
        }
    }

    /**
     * Получаем Lazy<Int> из аргументов фрагмента
     * Если такого аргумента нет, то возвращает -1
     * @param argKey ключ для получения аргумента
     */
    infix fun argInt(argKey: String): Lazy<Int> {
        return lazy {
            when(val args = arguments) {
                null -> -1
                else -> args.getInt(argKey)
            }
        }
    }

    /**
     * Получаем Lazy<Int> из аргументов фрагмента
     * Если такого аргумента нет, то возвращает -1F
     * @param arg ключ-значение аргумента, где ключ - ключ для получения аргумента, значение -
     * значение возвращаемое при отсутствии аргумента
     */
    infix fun argIntNullable(arg: Pair<String, Int>): Lazy<Int> {
        return lazy {
            when(val args = arguments) {
                null -> arg.second
                else -> args.getInt(arg.first, arg.second)
            }
        }
    }

    /**
     * Получаем Lazy<Float> из аргументов фрагмента
     * Если такого аргумента нет, то возвращает -1F
     * @param argKey ключ аргумента
     */
    infix fun argFloat(argKey: String): Lazy<Float> {
        return lazy {
            when(val args = arguments) {
                null -> -1F
                else -> args.getFloat(argKey)
            }
        }
    }

    /**
     * Получаем Lazy<T: Parcelable> из аргументов фрагмента
     * Если такого аргумента нет, то пыается вернуть "пустой" инстанс T
     * @param argKey ключ аргумента
     */
    infix fun <T: Parcelable> argParcelable(argKey: String): Lazy<T> {
        return lazy {
            @Suppress("UNCHECKED_CAST")
            when(val args = arguments) {
                null -> {
                    Timber.e("arguments is null!!")
                    object : Parcelable {
                        override fun describeContents(): Int = 0
                        override fun writeToParcel(dest: Parcel?, flags: Int) = Unit
                    } as T
                }
                else -> args.getParcelable<T>(argKey) as T
            }
        }
    }

    /**
     * Получаем Lazy<T: Parcelable?> из аргументов фрагмента
     * @param argKey ключ аргумента
     */
    infix fun <T: Parcelable> argParcelableNullable(argKey: String): Lazy<T?> {
        return lazy {
            when(val args = arguments) {
                null -> null
                else -> args.getParcelable<T>(argKey)
            }
        }
    }
    //endregion


}