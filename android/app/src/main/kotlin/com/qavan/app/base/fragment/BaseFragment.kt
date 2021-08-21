package com.qavan.app.base.fragment

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.qavan.app.base.IMVIObserver
import com.qavan.app.base.mvi.BaseModelViewIntent
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import com.qavan.app.extensions.EMPTY
import timber.log.Timber

abstract class BaseFragment<VS: BaseViewState, OSE: BaseOneShotEvent, FMVI: BaseModelViewIntent<VS, OSE>>: Fragment(),
    IMVIObserver<VS, OSE> {

    abstract val mvi: FMVI
    abstract val statusBarHeight: Int

    /**
     * SavedStateHandle для данных
     */
    val savedStateHandle: SavedStateHandle
        get() = mvi.state

    /**
     * NavController
     */
    val navController by lazy { findNavController() }

    abstract fun observeViewStates()
    abstract fun observeOneShotEvents()

    /**
     * Устанавливаем контекст в mvi и начинаем наблюдение за state'ами и event'ами
     * @see Fragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewStates()
        observeOneShotEvents()
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
     * Navigate with args
     * @param resId action/direction id
     * @param args Bundle of arguments
     */
    fun navigateWithArgs(
        resId: Int,
        args: Bundle,
        navOptions: NavOptions? = null,
        vararg shared: Pair<View, String>,
    ) {
        navigateIfAvailable(resId) {
            navController.navigate(
                resId = resId,
                args = args,
                navOptions = navOptions,
                navigatorExtras = FragmentNavigatorExtras(*shared)
            )
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
                else -> args.getParcelable(argKey)
            }
        }
    }
    //endregion


}