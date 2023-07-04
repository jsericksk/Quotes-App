package com.kproject.quotes.presentation.screens.auth.signup

sealed class SignUpUiEvent {
    data class EmailChanged(val email: String) : SignUpUiEvent()
    data class UsernameChanged(val username: String) : SignUpUiEvent()
    data class PasswordChanged(val password: String) : SignUpUiEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : SignUpUiEvent()
    object OnDismissErrorDialog : SignUpUiEvent()
}