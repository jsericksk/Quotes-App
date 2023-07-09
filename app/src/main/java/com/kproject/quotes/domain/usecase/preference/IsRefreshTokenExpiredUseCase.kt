package com.kproject.quotes.domain.usecase.preference

import kotlinx.coroutines.flow.Flow

interface IsRefreshTokenExpiredUseCase {
    operator fun invoke(): Flow<Boolean>
}