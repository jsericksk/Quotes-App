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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kproject.quotes.R
import com.kproject.quotes.presentation.screens.auth.components.AuthButton
import com.kproject.quotes.presentation.screens.auth.components.FieldType
import com.kproject.quotes.presentation.screens.auth.components.TextField
import com.kproject.quotes.presentation.theme.DefaultScreenPadding
import com.kproject.quotes.presentation.theme.PreviewTheme

@Composable
fun LoginScreen(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
) {
    MainContent(
        onNavigateToSignUpScreen = onNavigateToSignUpScreen
    )
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    onNavigateToSignUpScreen: () -> Unit
) {
    val heightSpacing = 24.dp

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            value = email,
            onValueChange = { newValue -> email = newValue },
            hint = stringResource(id = R.string.email),
            leadingIcon = R.drawable.outline_email_24,
            fieldType = FieldType.Email
        )
        Spacer(Modifier.height(heightSpacing))
        TextField(
            value = password,
            onValueChange = { newValue -> password = newValue },
            hint = stringResource(id = R.string.password),
            leadingIcon = R.drawable.outline_key_24,
            fieldType = FieldType.Password
        )
        Spacer(Modifier.height(heightSpacing))
        AuthButton(
            text = stringResource(id = R.string.login),
            onClick = {}
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
            annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
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
        LoginScreen(
            onNavigateToHomeScreen = {},
            onNavigateToSignUpScreen = {}
        )
    }
}