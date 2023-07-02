package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.exception.ValidationState

interface ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationState
}