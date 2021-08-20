package com.qavan.app.data.source.local

import android.content.SharedPreferences
import com.qavan.app.extensions.android.delegate

interface DevicePreferencesDataSource {
    var statusBarHeight: Int
}

class DevicePreferencesDataSourceImpl (
    preferences: SharedPreferences
): BaseDataSource(preferences), DevicePreferencesDataSource {

    companion object {
        private const val PREF_STATUS_BAR_HEIGHT = "PREF_STATUS_BAR_HEIGHT"
    }

    override var statusBarHeight by preferences.delegate(PREF_STATUS_BAR_HEIGHT, 0)
}