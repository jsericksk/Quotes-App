package com.kproject.quotes.commom.exception

sealed class ValidationState(val errorMessage: String) {
    object Success : ValidationState("")
    class EmptyEmail(val error: String) : ValidationState(error)
    class InvalidEmail(val error: String) : ValidationState(error)
    class EmptyPassword(val error: String) : ValidationState(error)
    class PasswordTooShort(val error: String) : ValidationState(error)
    class InvalidPassword(val error: String) : ValidationState(error)
    class RepeatedPasswordDoesNotMatch(val error: String) : ValidationState(error)
    class EmptyUsername(val error: String) : ValidationState(error)
    class InvalidUsername(val error: String) : ValidationState(error)
}