package com.kproject.quotes.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    fun <T> getPreference(key: String, defaultValue: T): T

    fun <T> savePreference(key: String, value: T)

    fun isRefreshTokenExpired(): Flow<Boolean>
}