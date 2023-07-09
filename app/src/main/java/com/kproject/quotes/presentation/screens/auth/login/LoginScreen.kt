package com.kproject.quotes.presentation.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kproject.quotes.R
import com.kproject.quotes.presentation.screens.auth.components.AuthButton
import com.kproject.quotes.presentation.screens.auth.components.FieldType
import com.kproject.quotes.presentation.screens.auth.components.TextField
import com.kproject.quotes.presentation.screens.components.ProgressAlertDialog
import com.kproject.quotes.presentation.screens.components.SimpleAlertDialog
import com.kproject.quotes.presentation.theme.DefaultScreenPadding
import com.kproject.quotes.presentation.theme.PreviewTheme

@Composable
fun LoginScreen(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val uiState = loginViewModel.uiState
    val isUserLoggedIn = uiState.isUserLoggedIn

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn) {
            onNavigateToHomeScreen.invoke()
        }
    }

    if (!isUserLoggedIn) {
        MainContent(
            onNavigateToSignUpScreen = onNavigateToSignUpScreen,
            uiState = uiState,
            onUiEvent = loginViewModel::onUiEvent,
            onLoginButtonClick = {
                loginViewModel.login()
            }
        )

        ProgressAlertDialog(showDialog = uiState.isLoading)

        SimpleAlertDialog(
            showDialog = uiState.loginError,
            onDismiss = {
                loginViewModel.onUiEvent(LoginUiEvent.OnDismissErrorDialog)
            },
            iconResId = R.drawable.outline_error_outline_24,
            title = stringResource(id = R.string.error),
            message = uiState.loginErrorMessage.asString(),
            showButtonCancel = false,
            cancelable = false,
            onClickButtonOk = {}
        )
    }
}

@Composable
private fun MainContent(
    uiState: LoginUiState,
    onNavigateToSignUpScreen: () -> Unit,
    onUiEvent: (LoginUiEvent) -> Unit,
    onLoginButtonClick: () -> Unit,
) {
    val heightSpacing = 24.dp

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(DefaultScreenPadding)
    ) {
        Text(
            text = stringResource(id = R.string.login),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(Modifier.height(50.dp))

        TextField(
            value = uiState.email,
            onValueChange = { value ->
                onUiEvent.invoke(LoginUiEvent.EmailChanged(value))
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
                onUiEvent.invoke(LoginUiEvent.PasswordChanged(value))
            },
            hint = stringResource(id = R.string.password),
            leadingIcon = R.drawable.outline_key_24,
            fieldType = FieldType.Password,
            errorMessage = uiState.passwordError.asString()
        )

        Spacer(Modifier.height(heightSpacing))

        AuthButton(
            text = stringResource(id = R.string.login),
            onClick = onLoginButtonClick
        )

        Spacer(Modifier.height(32.dp))

        SignUpText(
            onNavigateToSignUpScreen = onNavigateToSignUpScreen
        )
    }
}

@Composable
private fun SignUpText(onNavigateToSignUpScreen: () -> Unit) {
    val tag = stringResource(id = R.string.signup)
    val annotatedText = buildAnnotatedString {
        append(stringResource(id = R.string.dont_have_an_account) + " ")
        pushStringAnnotation(
            tag = tag,
            annotation = "signup"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(stringResource(id = R.string.signup))
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = TextStyle(
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp
        ),
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = tag, start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                onNavigateToSignUpScreen()
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    PreviewTheme {
        MainContent(
            uiState = LoginUiState(),
            onNavigateToSignUpScreen = {},
            onUiEvent = {},
            onLoginButtonClick = {}
        )
    }
}