package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.validation.AuthValidationState

class ValidatePasswordUseCaseImpl : ValidatePasswordUseCase {

    override fun invoke(password: String): AuthValidationState {
        if (password.isBlank()) {
            return AuthValidationState.EmptyPassword
        }
        if (password.length < 6) {
            return AuthValidationState.PasswordTooShort
        }
        return AuthValidationState.Success
    }
}