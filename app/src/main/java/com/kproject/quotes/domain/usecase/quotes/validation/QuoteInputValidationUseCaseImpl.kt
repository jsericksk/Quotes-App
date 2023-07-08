package com.kproject.quotes.domain.usecase.quotes.validation

import com.kproject.quotes.commom.validation.QuoteValidationState

class QuoteInputValidationUseCaseImpl : QuoteInputValidationUseCase {

    override fun invoke(quote: String, author: String): QuoteValidationState {
        if (quote.isBlank() || quote.length > 1000) {
            return QuoteValidationState.QuoteTextInvalid
        }
        if (quote.isBlank() || author.length > 80) {
            return QuoteValidationState.QuoteAuthorInvalid
        }
        return QuoteValidationState.Success
    }
}