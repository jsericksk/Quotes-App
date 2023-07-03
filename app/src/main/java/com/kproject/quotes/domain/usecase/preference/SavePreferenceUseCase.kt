package com.kproject.quotes.domain.usecase.preference

interface SavePreferenceUseCase {
    operator fun <T> invoke(key: String, value: T)
}