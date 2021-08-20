package com.qavan.app.base.mvi

import com.qavan.app.base.activity.IDeviceProvider
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState
import com.qavan.app.data.source.local.DevicePreferencesDataSource
import com.qavan.app.extensions.MVI
import kotlinx.coroutines.flow.MutableStateFlow

@Suppress("KDocMissingDocumentation")
abstract class BaseFragmentMVI<VS: BaseViewState, OSE: BaseOneShotEvent>: MVI<VS, OSE>(),
    IDeviceProvider {

    abstract val devicePreferences: DevicePreferencesDataSource

    /**
     * Высота статусбара устройства
     */
    override val statusBarHeight: Int
        get() = devicePreferences.statusBarHeight

    /**
     * Класс хелпер для MutableStateFlow значения
     */
    inner class Delegator<T>(
        /**
         * Ключ для значения
         */
        val key: String,
        defValue: T,
    ) {

        /**
         * MutableStateFlow значение
         */
        val flow: MutableStateFlow<T> by lazy { MutableStateFlow(state.get<T>(key) ?: defValue) }


        /**
         * текущее значение
         */
        var value: T
            get() = flow.value
            set(value) {
                state.set(key, value)
                flow.value = value
            }
    }

}