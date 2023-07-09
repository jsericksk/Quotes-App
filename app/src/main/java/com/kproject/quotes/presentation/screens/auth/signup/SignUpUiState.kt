package com.kproject.quotes.presentation.screens.auth.signup

import com.kproject.quotes.domain.model.auth.SignUpModel
import com.kproject.quotes.presentation.utils.UiText

data class SignUpUiState(
    val email: String = "",
    val emailError: UiText = UiText.HardcodedString(""),
    val username: String = "",
    val usernameError: UiText = UiText.HardcodedString(""),
    val password: String = "",
    val passwordError: UiText = UiText.HardcodedString(""),
    val repeatedPassword: String = "",
    val repeatedPasswordError: UiText = UiText.HardcodedString(""),
    val isLoading: Boolean = false,
    val signUpOrAutoLoginError: Boolean = false,
    val signUpOrAutoLoginErrorMessage: UiText = UiText.HardcodedString(""),
    val validateFieldsWhenTyping: Boolean = false
)

fun SignUpUiState.toSignUpModel() = SignUpModel(email, username, password)