package com.kproject.quotes.presentation.screens.auth.login

import com.kproject.quotes.domain.model.auth.Login
import com.kproject.quotes.presentation.utils.UiText

data class LoginUiState(
    val email: String = "",
    val emailError: UiText = UiText.HardcodedString(""),
    val password: String = "",
    val passwordError: UiText = UiText.HardcodedString(""),
    val isLoading: Boolean = false,
    val loginError: Boolean = false,
    val loginErrorMessage: UiText = UiText.HardcodedString(""),
    val validateFieldsWhenTyping: Boolean = false
)

fun LoginUiState.toLoginModel() = Login(email, password)