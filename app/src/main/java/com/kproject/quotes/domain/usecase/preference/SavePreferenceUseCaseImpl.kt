package com.kproject.quotes.domain.usecase.preference

import com.kproject.quotes.domain.repository.PreferenceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavePreferenceUseCaseImpl @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : SavePreferenceUseCase {

    override fun <T> invoke(key: String, value: T) {
        preferenceRepository.savePreference(key, value)
    }
}