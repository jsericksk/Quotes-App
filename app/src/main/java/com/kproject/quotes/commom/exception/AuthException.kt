package com.kproject.quotes.commom.exception

sealed class AuthenticationException : BaseException() {
    object EmailNotAvailableException : AuthenticationException()
    object UsernameNotAvailableException : AuthenticationException()
    object WrongEmailOrPasswordException : AuthenticationException()
    object UnknownLoginException : AuthenticationException()
    object UnknownSignUpException : AuthenticationException()
}