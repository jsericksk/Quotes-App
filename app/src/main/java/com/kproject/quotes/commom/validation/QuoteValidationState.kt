package com.kproject.quotes.commom.validation

sealed class QuoteValidationState {
    object Success : QuoteValidationState()
    object QuoteTextInvalid : QuoteValidationState()
    object QuoteAuthorInvalid : QuoteValidationState()
}