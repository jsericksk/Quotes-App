package com.kproject.quotes.domain.usecase.preference

import com.kproject.quotes.domain.repository.PreferenceRepository

class GetPreferenceUseCaseImpl(
    private val preferenceRepository: PreferenceRepository
) : GetPreferenceUseCase {

    override fun <T> invoke(key: String, defaultValue: T): T {
        return preferenceRepository.getPreference(key, defaultValue)
    }
}