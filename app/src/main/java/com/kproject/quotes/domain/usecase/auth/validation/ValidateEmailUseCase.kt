package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.validation.AuthValidationState

interface ValidateEmailUseCase {
    operator fun invoke(email: String): AuthValidationState
}