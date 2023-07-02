package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.exception.ValidationState

interface ValidateRepeatedPasswordUseCase {
    operator fun invoke(password: String, repeatedPassword: String): ValidationState
}