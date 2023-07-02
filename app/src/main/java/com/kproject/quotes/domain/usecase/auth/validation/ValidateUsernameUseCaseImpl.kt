package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.exception.ValidationState

class ValidateUsernameUseCaseImpl : ValidateUsernameUseCase {

    override fun invoke(username: String): ValidationState {
        if (username.isBlank()) {
            return ValidationState.EmptyUsername
        }
        if (username.length < 3 || username.length > 50) {
            return ValidationState.InvalidUsername
        }
        return ValidationState.Success
    }
}