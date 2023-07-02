package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.exception.ValidationState

class ValidateRepeatedPasswordUseCaseImpl : ValidateRepeatedPasswordUseCase {

    override fun invoke(password: String, repeatedPassword: String): ValidationState {
        if (repeatedPassword != password) {
            return ValidationState.RepeatedPasswordDoesNotMatch
        }
        return ValidationState.Success
    }
}