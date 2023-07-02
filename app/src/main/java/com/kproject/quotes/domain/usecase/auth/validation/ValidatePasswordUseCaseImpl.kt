package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.exception.ValidationState

class ValidatePasswordUseCaseImpl : ValidatePasswordUseCase {

    override fun invoke(password: String): ValidationState {
        if (password.isBlank()) {
            return ValidationState.EmptyPassword
        }
        if (password.length < 6) {
            return ValidationState.PasswordTooShort
        }
        return ValidationState.Success
    }
}