package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.exception.ValidationState

class ValidateEmailUseCaseImpl(
    private val emailValidator: EmailValidator
) : ValidateEmailUseCase {

    override fun invoke(email: String): ValidationState {
        if (email.isBlank()) {
            return ValidationState.EmptyEmail
        }
        if (!emailValidator.isValidEmail(email)) {
            return ValidationState.InvalidEmail
        }
        return ValidationState.Success
    }
}