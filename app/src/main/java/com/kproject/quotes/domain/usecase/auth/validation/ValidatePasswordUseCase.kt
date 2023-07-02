package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.exception.ValidationState

interface ValidatePasswordUseCase {
    operator fun invoke(password: String): ValidationState
}