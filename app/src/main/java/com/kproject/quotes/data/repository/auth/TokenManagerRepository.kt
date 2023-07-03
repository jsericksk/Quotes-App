package com.kproject.quotes.data.repository.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.kproject.quotes.commom.constants.PrefsConstants
import dagger.hilt.android.qualifiers.ApplicationContext

class TokenManagerRepository(
    @ApplicationContext private val context: Context,
) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = initializeEncryptedSharedPreferences()
    }

    private fun initializeEncryptedSharedPreferences(): SharedPreferences {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "tokens",
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(PrefsConstants.AccessToken, accessToken).apply()
    }

    fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(PrefsConstants.RefreshToken, refreshToken).apply()
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString(PrefsConstants.AccessToken, "") ?: ""
    }

    fun getRefreshToken(): String {
        return sharedPreferences.getString(PrefsConstants.RefreshToken, "") ?: ""
    }
}