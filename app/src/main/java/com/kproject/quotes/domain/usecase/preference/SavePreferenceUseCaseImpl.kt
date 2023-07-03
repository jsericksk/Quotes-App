package com.kproject.quotes.domain.usecase.preference

import com.kproject.quotes.domain.repository.PreferenceRepository

class SavePreferenceUseCaseImpl(
    private val preferenceRepository: PreferenceRepository
) : SavePreferenceUseCase {

    override fun <T> invoke(key: String, value: T) {
        preferenceRepository.savePreference(key, value)
    }
}