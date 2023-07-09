package com.kproject.quotes.presentation.utils

import com.kproject.quotes.R
import com.kproject.quotes.commom.exception.AuthException
import com.kproject.quotes.commom.exception.BaseException
import com.kproject.quotes.commom.exception.QuoteException
import com.kproject.quotes.commom.validation.AuthValidationState
import com.kproject.quotes.commom.validation.QuoteValidationState

fun AuthValidationState.toErrorMessage(): UiText {
    return when (this) {
        AuthValidationState.EmptyEmail -> UiText.StringResource(R.string.error_empty_email)
        AuthValidationState.InvalidEmail -> UiText.StringResource(R.string.error_email_badly_formatted)
        AuthValidationState.EmptyPassword -> UiText.StringResource(R.string.error_empty_password)
        AuthValidationState.PasswordTooShort -> UiText.StringResource(R.string.error_password_too_short)
        AuthValidationState.RepeatedPasswordDoesNotMatch -> UiText.StringResource(R.string.error_passwords_does_not_match)
        AuthValidationState.EmptyUsername -> UiText.StringResource(R.string.error_username_empty)
        AuthValidationState.InvalidUsername -> UiText.StringResource(R.string.error_invalid_username)
        else -> UiText.HardcodedString("")
    }
}

fun QuoteValidationState.toErrorMessage(): UiText {
    return when (this) {
        QuoteValidationState.QuoteTextInvalid -> UiText.StringResource(R.string.error_quote_text_invalid)
        QuoteValidationState.QuoteAuthorInvalid -> UiText.StringResource(R.string.error_quote_author_invalid)
        else -> UiText.HardcodedString("")
    }
}

fun BaseException.toAuthErrorMessage(): UiText  {
    return when (this) {
        AuthException.EmailNotAvailableException -> UiText.StringResource(R.string.error_email_already_exists)
        AuthException.UsernameNotAvailableException -> UiText.StringResource(R.string.error_username_already_exists)
        AuthException.WrongEmailOrPasswordException -> UiText.StringResource(R.string.error_wrong_email_or_password)
        AuthException.UnknownLoginException -> UiText.StringResource(R.string.error_login)
        AuthException.UnknownSignUpException -> UiText.StringResource(R.string.error_signup)
        else -> UiText.StringResource(R.string.unknown_error)
    }
}

fun BaseException.toQuoteErrorMessage(): UiText  {
    return when (this) {
        QuoteException.NoQuoteFound -> UiText.StringResource(R.string.no_quote_found_in_search)
        QuoteException.UserWithoutPosts -> UiText.StringResource(R.string.user_without_posts)
        QuoteException.QuoteDoesNotExist -> UiText.StringResource(R.string.quote_does_not_exist)
        else -> UiText.StringResource(R.string.error_loading_quotes)
    }
}