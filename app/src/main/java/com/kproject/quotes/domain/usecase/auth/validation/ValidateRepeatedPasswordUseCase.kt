package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.validation.AuthValidationState

interface ValidateRepeatedPasswordUseCase {
    operator fun invoke(password: String, repeatedPassword: String): AuthValidationState
}