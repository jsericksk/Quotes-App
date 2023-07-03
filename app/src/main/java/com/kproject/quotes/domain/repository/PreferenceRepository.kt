package com.kproject.quotes.domain.repository

interface PreferenceRepository {

    fun <T> getPreference(key: String, defaultValue: T): T

    fun <T> savePreference(key: String, value: T)
}