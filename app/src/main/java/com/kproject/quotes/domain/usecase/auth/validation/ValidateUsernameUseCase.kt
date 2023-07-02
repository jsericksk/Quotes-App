package com.kproject.quotes.domain.usecase.auth.validation

import com.kproject.quotes.commom.exception.ValidationState

interface ValidateUsernameUseCase {
    operator fun invoke(username: String): ValidationState
}