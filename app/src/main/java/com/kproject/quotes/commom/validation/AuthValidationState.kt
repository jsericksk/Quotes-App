package com.kproject.quotes.commom.validation

sealed class AuthValidationState {
    object Success : AuthValidationState()
    object EmptyEmail : AuthValidationState()
    object InvalidEmail : AuthValidationState()
    object EmptyPassword : AuthValidationState()
    object PasswordTooShort : AuthValidationState()
    object RepeatedPasswordDoesNotMatch : AuthValidationState()
    object EmptyUsername : AuthValidationState()
    object InvalidUsername : AuthValidationState()
}