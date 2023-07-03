package com.kproject.quotes.commom.exception

sealed class ValidationState {
    object Success : ValidationState()
    object EmptyEmail : ValidationState()
    object InvalidEmail : ValidationState()
    object EmptyPassword : ValidationState()
    object PasswordTooShort : ValidationState()
    object RepeatedPasswordDoesNotMatch : ValidationState()
    object EmptyUsername : ValidationState()
    object InvalidUsername : ValidationState()
}