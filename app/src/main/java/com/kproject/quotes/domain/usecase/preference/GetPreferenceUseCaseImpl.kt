package com.kproject.quotes.domain.usecase.preference

import com.kproject.quotes.domain.repository.PreferenceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPreferenceUseCaseImpl @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : GetPreferenceUseCase {

    override fun <T> invoke(key: String, defaultValue: T): T {
        return preferenceRepository.getPreference(key, defaultValue)
    }
}