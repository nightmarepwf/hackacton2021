package com.qavan.app.data.source.local

import android.content.SharedPreferences
import com.qavan.app.extensions.EMPTY
import com.qavan.app.extensions.android.delegate

interface PushDataSource {
    var currentToken: String
}

class PushDataSourceImpl (
    sharedPreferences: SharedPreferences,
): BaseDataSource(sharedPreferences), PushDataSource {

    companion object {
        private const val PREF_PUSH_TOKEN = "PREF_PUSH_TOKEN"
    }

    override var currentToken by preferences.delegate(PREF_PUSH_TOKEN, String.EMPTY)

}