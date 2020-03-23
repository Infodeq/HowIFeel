package com.slaw.howifeel.data.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

interface SharedPreferenceManager {

    var shownSymptom: Boolean
    var clientToken: String
}
@Singleton
class SharedPreferenceManagerImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): SharedPreferenceManager {
    override var shownSymptom: Boolean
        get() = sharedPreferences.getBoolean(SHOWN_SYMPTOM, false)
        set(value) {
            sharedPreferences.edit().putBoolean(SHOWN_SYMPTOM, value).apply()
        }

    override var clientToken: String
        get() = sharedPreferences.getString(CLIENT_TOKEN, "")?:""
        set(value) {
            sharedPreferences.edit().putString(CLIENT_TOKEN, value).apply()
        }

    companion object {
        const val CLIENT_TOKEN = "client_token"
        const val SHOWN_SYMPTOM = "shown_symptom"
    }
}