package com.kproject.quotes.presentation.utils

import com.kproject.quotes.R
import com.kproject.quotes.commom.exception.AuthException
import com.kproject.quotes.commom.exception.BaseException
import com.kproject.quotes.commom.exception.QuoteException
import com.kproject.quotes.commom.exception.ValidationState

fun ValidationState.toErrorMessage(): UiText {
    return when (this) {
        ValidationState.EmptyEmail -> UiText.StringResource(R.string.error_empty_email)
        ValidationState.InvalidEmail -> UiText.StringResource(R.string.error_email_badly_formatted)
        ValidationState.EmptyPassword -> UiText.StringResource(R.string.error_empty_password)
        ValidationState.PasswordTooShort -> UiText.StringResource(R.string.error_password_too_short)
        ValidationState.RepeatedPasswordDoesNotMatch -> UiText.StringResource(R.string.error_passwords_does_not_match)
        ValidationState.EmptyUsername -> UiText.StringResource(R.string.error_username_empty)
        ValidationState.InvalidUsername -> UiText.StringResource(R.string.error_invalid_username)
        else -> UiText.HardcodedString("")
    }
}

fun BaseException.toAuthErrorMessage(): UiText  {
    return when (this) {
        AuthException.EmailNotAvailableException -> UiText.StringResource(R.string.error_email_not_available)
        AuthException.UsernameNotAvailableException -> UiText.StringResource(R.string.error_username_not_available)
        AuthException.WrongEmailOrPasswordException -> UiText.StringResource(R.string.error_wrong_email_or_password)
        AuthException.UnknownLoginException -> UiText.StringResource(R.string.error_login)
        AuthException.UnknownSignUpException -> UiText.StringResource(R.string.error_signup)
        else -> UiText.StringResource(R.string.unknown_error)
    }
}

fun BaseException.toQuoteErrorMessage(): UiText  {
    return when (this) {
        QuoteException.NoQuoteFound -> UiText.StringResource(R.string.no_quote_found)
        QuoteException.UnknownError -> UiText.StringResource(R.string.error_loading_quotes)
        else -> UiText.StringResource(R.string.error_loading_quotes)
    }
}