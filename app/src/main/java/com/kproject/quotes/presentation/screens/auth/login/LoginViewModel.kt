package com.kproject.quotes.presentation.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.commom.exception.ValidationState
import com.kproject.quotes.domain.usecase.auth.LoginUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidateEmailUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidatePasswordUseCase
import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCase
import com.kproject.quotes.domain.usecase.preference.SavePreferenceUseCase
import com.kproject.quotes.domain.usecase.preference.SavePreferenceUseCaseImpl
import com.kproject.quotes.presentation.screens.auth.utils.toAuthErrorMessage
import com.kproject.quotes.presentation.screens.auth.utils.toErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    var loginState: ResultState<Unit>? by mutableStateOf(null)
        private set

    fun onUiEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                uiState = uiState.copy(email = event.email)
                validateFieldsWhenTyping()
            }
            is LoginUiEvent.PasswordChanged -> {
                uiState = uiState.copy(password = event.password)
                validateFieldsWhenTyping()
            }
            is LoginUiEvent.OnDismissErrorDialog -> {
                uiState = uiState.copy(loginError = false)
            }
        }
    }

    fun login() {
        if (hasValidationError()) {
            uiState = uiState.copy(validateFieldsWhenTyping = true)
        } else {
            viewModelScope.launch {
                val loginResult = loginUseCase(uiState.toLoginModel())
                loginResult.collect { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            uiState = uiState.copy(isLoading = true)
                        }
                        is ResultState.Success -> {
                            loginState = ResultState.Success()
                            uiState = uiState.copy(isLoading = false)
                        }
                        is ResultState.Error -> {
                            result.exception?.let { exception ->
                                uiState = uiState.copy(
                                    isLoading = false,
                                    loginError = true,
                                    loginErrorMessage = exception.toAuthErrorMessage()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun hasValidationError(): Boolean {
        val emailValidationState = validateEmailUseCase(uiState.email)
        uiState = uiState.copy(emailError = emailValidationState.toErrorMessage())
        val passwordValidationState = validatePasswordUseCase(uiState.password)
        uiState = uiState.copy(passwordError = passwordValidationState.toErrorMessage())

        val hasError = listOf(
            emailValidationState,
            passwordValidationState
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