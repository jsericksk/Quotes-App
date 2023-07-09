package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.validation.AuthValidationState

interface ValidateUsernameUseCase {
    operator fun invoke(username: String): AuthValidationState
}