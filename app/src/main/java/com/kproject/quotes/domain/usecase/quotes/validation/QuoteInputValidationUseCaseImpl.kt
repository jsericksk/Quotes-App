package com.kproject.quotes.domain.usecase.quotes.validation

import com.kproject.quotes.commom.validation.QuoteValidationState

class QuoteInputValidationUseCaseImpl : QuoteInputValidationUseCase {

    override fun invoke(quote: String, author: String): QuoteValidationState {
        if (quote.length < 7 || quote.length > 1000) {
            return QuoteValidationState.QuoteTextInvalid
        }
        if (author.isBlank() || author.length > 80) {
            return QuoteValidationState.QuoteAuthorInvalid
        }
        return QuoteValidationState.Success
    }
}