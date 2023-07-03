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
    private val sharedPreferences: SharedPreferences by lazy {
        initializeEncryptedSharedPreferences()
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

    var accessToken: String
        get() = sharedPreferences.getString(PrefsConstants.AccessToken, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PrefsConstants.AccessToken, value).apply()

    var refreshToken: String
        get() = sharedPreferences.getString(PrefsConstants.RefreshToken, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PrefsConstants.RefreshToken, value).apply()
}