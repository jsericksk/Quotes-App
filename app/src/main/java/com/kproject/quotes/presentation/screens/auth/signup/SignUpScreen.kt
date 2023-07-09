package com.kproject.quotes.presentation.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kproject.quotes.R
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.presentation.screens.auth.components.AuthButton
import com.kproject.quotes.presentation.screens.auth.components.FieldType
import com.kproject.quotes.presentation.screens.auth.components.TextField
import com.kproject.quotes.presentation.screens.components.ProgressAlertDialog
import com.kproject.quotes.presentation.screens.components.SimpleAlertDialog
import com.kproject.quotes.presentation.theme.DefaultScreenPadding
import com.kproject.quotes.presentation.theme.PreviewTheme

@Composable
fun SignUpScreen(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val uiState = signUpViewModel.uiState
    val signUpState = signUpViewModel.signUpState

    MainContent(
        uiState = uiState,
        onUiEvent = signUpViewModel::onUiEvent,
        onSignUpButtonClick = {
            signUpViewModel.signUp()
        },
        onNavigateBack = onNavigateBack,
    )

    LaunchedEffect(signUpState) {
        if (signUpState is ResultState.Success) {
            onNavigateToHomeScreen.invoke()
        }
    }

    ProgressAlertDialog(showDialog = uiState.isLoading)

    SimpleAlertDialog(
        showDialog = uiState.signUpOrAutoLoginError,
        onDismiss = {
            signUpViewModel.onUiEvent(SignUpUiEvent.OnDismissErrorDialog)
        },
        iconResId = R.drawable.outline_error_outline_24,
        title = stringResource(id = R.string.error),
        message = uiState.signUpOrAutoLoginErrorMessage.asString(),
        showButtonCancel = false,
        cancelable = false,
        onClickButtonOk = {},
    )
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onUiEvent: (SignUpUiEvent) -> Unit,
    onSignUpButtonClick: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val heightSpacing = 24.dp

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(34.dp)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(DefaultScreenPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.signup),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(50.dp))

            TextField(
                value = uiState.email,
                onValueChange = { value ->
                    onUiEvent.invoke(SignUpUiEvent.EmailChanged(value))
                },
                hint = stringResource(id = R.string.email),
                leadingIcon = R.drawable.outline_email_24,
                fieldType = FieldType.Email,
                errorMessage = uiState.emailError.asString()
            )

            Spacer(Modifier.height(heightSpacing))

            TextField(
                value = uiState.password,
                onValueChange = { value ->
                    onUiEvent.invoke(SignUpUiEvent.PasswordChanged(value))
                },
                hint = stringResource(id = R.string.password),
                leadingIcon = R.drawable.outline_key_24,
                fieldType = FieldType.Password,
                errorMessage = uiState.passwordError.asString()
            )

            Spacer(Modifier.height(heightSpacing))

            TextField(
                value = uiState.repeatedPassword,
                onValueChange = { value ->
                    onUiEvent.invoke(SignUpUiEvent.RepeatedPasswordChanged(value))
                },
                hint = stringResource(id = R.string.repeated_password),
                leadingIcon = R.drawable.outline_key_24,
                fieldType = FieldType.Password,
                errorMessage = uiState.repeatedPasswordError.asString()
            )

            Spacer(Modifier.height(heightSpacing))

            TextField(
                value = uiState.username,
                onValueChange = { value ->
                    onUiEvent.invoke(SignUpUiEvent.UsernameChanged(value))
                },
                hint = stringResource(id = R.string.username),
                leadingIcon = R.drawable.outline_person_24,
                errorMessage = uiState.usernameError.asString()
            )

            Spacer(Modifier.height(heightSpacing))

            AuthButton(
                text = stringResource(id = R.string.signup),
                onClick = onSignUpButtonClick
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewTheme {
        MainContent(
            uiState = SignUpUiState(),
            onNavigateBack = {},
            onUiEvent = {},
            onSignUpButtonClick = {}
        )
    }
}