package com.kproject.quotes.domain.usecase.preference

import com.kproject.quotes.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsRefreshTokenExpiredUseCaseImpl @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : IsRefreshTokenExpiredUseCase {

    override fun invoke(): Flow<Boolean> {
        return preferenceRepository.isRefreshTokenExpired()
    }
}