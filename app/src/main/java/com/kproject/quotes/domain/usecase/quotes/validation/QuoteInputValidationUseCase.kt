package com.kproject.quotes.domain.usecase.quotes.validation

import com.kproject.quotes.commom.validation.QuoteValidationState

interface QuoteInputValidationUseCase {
    operator fun invoke(quote: String, author: String): QuoteValidationState
}