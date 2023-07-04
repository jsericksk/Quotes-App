package com.kproject.quotes.presentation.screens.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.quotes.R
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.commom.exception.ValidationState
import com.kproject.quotes.domain.model.auth.Login
import com.kproject.quotes.domain.usecase.auth.LoginUseCase
import com.kproject.quotes.domain.usecase.auth.SignUpUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidateEmailUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidatePasswordUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidateRepeatedPasswordUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidateUsernameUseCase
import com.kproject.quotes.domain.usecase.preference.SavePreferenceUseCase
import com.kproject.quotes.presentation.screens.auth.utils.toAuthErrorMessage
import com.kproject.quotes.presentation.screens.auth.utils.toErrorMessage
import com.kproject.quotes.presentation.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase,
    private val savePreferenceUseCase: SavePreferenceUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateRepeatedPasswordUseCase: ValidateRepeatedPasswordUseCase
) : ViewModel() {
    var uiState by mutableStateOf(SignUpUiState())
        private set

    var signUpState: ResultState<Unit>? by mutableStateOf(null)
        private set

    fun onUiEvent(event: SignUpUiEvent) {
        when (event) {
            is SignUpUiEvent.EmailChanged -> {
                uiState = uiState.copy(email = event.email)
                validateFieldsWhenTyping()
            }

            is SignUpUiEvent.UsernameChanged -> {
                uiState = uiState.copy(username = event.username)
                validateFieldsWhenTyping()
            }

            is SignUpUiEvent.PasswordChanged -> {
                uiState = uiState.copy(password = event.password)
                validateFieldsWhenTyping()
            }

            is SignUpUiEvent.RepeatedPasswordChanged -> {
                uiState = uiState.copy(repeatedPassword = event.repeatedPassword)
                validateFieldsWhenTyping()
            }

            is SignUpUiEvent.OnDismissErrorDialog -> {
                uiState = uiState.copy(signUpError = false)
            }
        }
    }

    fun signUp() {
        if (hasValidationError()) {
            uiState = uiState.copy(validateFieldsWhenTyping = true)
        } else {
            uiState = uiState.copy(isLoading = true)
            viewModelScope.launch {
                val signUpResult = signUpUseCase(uiState.toSignUpModel())
                signUpResult.collect { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            uiState = uiState.copy(isLoading = true)
                        }

                        is ResultState.Success -> {
                            autoLogin()
                        }

                        is ResultState.Error -> {
                            result.exception?.let { exception ->
                                uiState = uiState.copy(
                                    isLoading = false,
                                    signUpError = true,
                                    signUpErrorMessage = exception.toAuthErrorMessage()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun autoLogin() {
        viewModelScope.launch {
            val login = Login(uiState.email, uiState.password)
            loginUseCase(login).collect { loginResult ->
                when (loginResult) {
                    is ResultState.Success -> {
                        uiState = uiState.copy(isLoading = false)
                        signUpState = ResultState.Success()
                        savePreferenceUseCase(
                            key = PrefsConstants.IsUserLoggedIn,
                            value = true
                        )
                    }

                    is ResultState.Error -> {
                        uiState = uiState.copy(
                            isLoading = false,
                            signUpError = true,
                            signUpErrorMessage = UiText.StringResource(R.string.error_when_trying_to_auto_login)
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun hasValidationError(): Boolean {
        val usernameValidationState = validateUsernameUseCase(uiState.username)
        uiState = uiState.copy(usernameError = usernameValidationState.toErrorMessage())
        val emailValidationState = validateEmailUseCase(uiState.email)
        uiState = uiState.copy(emailError = emailValidationState.toErrorMessage())
        val passwordValidationState = validatePasswordUseCase(uiState.password)
        uiState = uiState.copy(passwordError = passwordValidationState.toErrorMessage())
        val repeatedPasswordValidationState = validateRepeatedPasswordUseCase(
            uiState.password, uiState.repeatedPassword
        )
        uiState = uiState.copy(
            repeatedPasswordError = repeatedPasswordValidationState.toErrorMessage()
        )

        val hasError = listOf(
            usernameValidationState,
            emailValidationState,
            passwordValidationState,
            repeatedPasswordValidationState
        ).any { validationState ->
            validationState != ValidationState.Success
        }

        return hasError
    }

    private fun validateFieldsWhenTyping() {
        if (uiState.validateFieldsWhenTyping) {
            hasValidationError()
        }
    }
}