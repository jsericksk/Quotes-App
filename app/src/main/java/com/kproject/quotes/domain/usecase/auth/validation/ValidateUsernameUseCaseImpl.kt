package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.validation.AuthValidationState

class ValidateUsernameUseCaseImpl : ValidateUsernameUseCase {

    override fun invoke(username: String): AuthValidationState {
        if (username.isBlank()) {
            return AuthValidationState.EmptyUsername
        }
        if (username.length < 3 || username.length > 50) {
            return AuthValidationState.InvalidUsername
        }
        return AuthValidationState.Success
    }
}