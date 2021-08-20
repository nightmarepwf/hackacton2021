package com.qavan.app.data.source.local

import android.content.SharedPreferences
import com.qavan.app.extensions.EMPTY
import com.qavan.app.extensions.android.delegate

interface UserDataSource {
    var userId: String
}

class UserDataSourceImpl (
    sharedPreferences: SharedPreferences,
): BaseDataSource(sharedPreferences), UserDataSource {

    companion object {
        private const val PREF_USER_ID = "PREF_USER_ID"
    }

    override var userId by preferences.delegate(PREF_USER_ID, String.EMPTY)

}