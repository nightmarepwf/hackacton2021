package com.qavan.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.qavan.app.data.source.local.DevicePreferencesDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity: ComponentActivity() {

    @Inject
    lateinit var devicePreferences: DevicePreferencesDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}