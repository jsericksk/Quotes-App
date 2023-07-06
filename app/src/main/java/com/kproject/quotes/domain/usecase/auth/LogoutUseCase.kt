package com.kproject.quotes.domain.usecase.auth

import com.kproject.quotes.commom.ResultState

fun interface LogoutUseCase {
    suspend operator fun invoke(): ResultState<Unit>
}