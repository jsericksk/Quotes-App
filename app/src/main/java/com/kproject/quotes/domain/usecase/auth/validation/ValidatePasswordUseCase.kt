package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.validation.AuthValidationState

interface ValidatePasswordUseCase {
    operator fun invoke(password: String): AuthValidationState
}