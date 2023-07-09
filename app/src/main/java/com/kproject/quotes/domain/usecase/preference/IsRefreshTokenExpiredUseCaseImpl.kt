package com.kproject.quotes.domain.usecase.preference

import com.kproject.quotes.data.repository.auth.TokenManagerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsRefreshTokenExpiredUseCaseImpl @Inject constructor(
    private val tokenManagerRepository: TokenManagerRepository
) : IsRefreshTokenExpiredUseCase {

    override fun invoke(): Flow<Boolean> {
        return tokenManagerRepository.isRefreshTokenExpired()
    }
}