package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.validation.AuthValidationState

class ValidateRepeatedPasswordUseCaseImpl : ValidateRepeatedPasswordUseCase {

    override fun invoke(password: String, repeatedPassword: String): AuthValidationState {
        if (repeatedPassword != password) {
            return AuthValidationState.RepeatedPasswordDoesNotMatch
        }
        return AuthValidationState.Success
    }
}