package com.kproject.quotes.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.domain.repository.PreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

private const val PrefsName = "settings"

class PreferenceRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val prefs: SharedPreferences = context.getSharedPreferences(PrefsName, 0)
) : PreferenceRepository {

    override fun <T> getPreference(key: String, defaultValue: T): T {
        return getValue(key, defaultValue)
    }

    override fun <T> savePreference(key: String, value: T) {
        saveValue(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getValue(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> prefs.getString(key, defaultValue as String) as T
            is Boolean -> prefs.getBoolean(key, defaultValue as Boolean) as T
            is Int -> prefs.getInt(key, defaultValue as Int) as T
            is Long -> prefs.getLong(key, defaultValue as Long) as T
            is Float -> prefs.getFloat(key, defaultValue as Float) as T
            else -> throw UnsupportedOperationException("Type provided as defaultValue not supported")
        }
    }

    private fun <T> saveValue(key: String, value: T) {
        when (value) {
            is String -> prefs.edit { putString(key, value) }
            is Boolean -> prefs.edit { putBoolean(key, value) }
            is Int -> prefs.edit { putInt(key, value) }
            is Long -> prefs.edit { putLong(key, value) }
            is Float -> prefs.edit { putFloat(key, value) }
            else -> throw UnsupportedOperationException("Type provided as defaultValue not supported")
        }
    }

    override fun isRefreshTokenExpired() = callbackFlow<Boolean> {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == PrefsConstants.RefreshTokenExpired) {
                trySend(prefs.getBoolean(key, false))
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        if (prefs.contains(PrefsConstants.RefreshTokenExpired)) {
            send(prefs.getBoolean(PrefsConstants.RefreshTokenExpired, false))
        }
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }.buffer(Channel.UNLIMITED)
}