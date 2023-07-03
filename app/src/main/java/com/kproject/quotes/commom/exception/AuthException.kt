package com.kproject.quotes.commom.exception

sealed class AuthException : BaseException() {
    object EmailNotAvailableException : AuthException()
    object UsernameNotAvailableException : AuthException()
    object WrongEmailOrPasswordException : AuthException()
    object UnknownLoginException : AuthException()
    object UnknownSignUpException : AuthException()
}