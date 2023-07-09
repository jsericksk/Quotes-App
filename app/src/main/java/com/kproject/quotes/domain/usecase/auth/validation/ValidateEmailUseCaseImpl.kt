package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.validation.AuthValidationState

class ValidateEmailUseCaseImpl(
    private val emailValidator: EmailValidator
) : ValidateEmailUseCase {

    override fun invoke(email: String): AuthValidationState {
        if (email.isBlank()) {
            return AuthValidationState.EmptyEmail
        }
        if (!emailValidator.isValidEmail(email)) {
            return AuthValidationState.InvalidEmail
        }
        return AuthValidationState.Success
    }
}