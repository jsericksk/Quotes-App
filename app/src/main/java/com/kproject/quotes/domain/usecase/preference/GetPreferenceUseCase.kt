package com.kproject.quotes.domain.usecase.preference

interface GetPreferenceUseCase {
    operator fun <T> invoke(key: String, defaultValue: T): T
}