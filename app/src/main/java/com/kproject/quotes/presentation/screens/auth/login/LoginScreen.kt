package com.kproject.quotes.presentation.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
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
    MainContent()
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
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
            text = "Login",
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
            text = "Login",
            onClick = {}
        )
    }
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