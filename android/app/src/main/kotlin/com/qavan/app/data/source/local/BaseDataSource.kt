package com.qavan.app.data.source.local

import android.content.SharedPreferences

abstract class BaseDataSource(
    sharedPreferences: SharedPreferences,
) {

    protected val preferences: SharedPreferences = sharedPreferences

}