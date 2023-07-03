package com.kproject.quotes.presentation.screens.auth.login

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    object OnDismissErrorDialog : LoginUiEvent()
}