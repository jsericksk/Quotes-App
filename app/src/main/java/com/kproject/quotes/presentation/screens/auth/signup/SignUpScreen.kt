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
import androidx.compose.material3.*
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
import com.kproject.quotes.presentation.screens.auth.login.LoginScreen
import com.kproject.quotes.presentation.theme.DefaultScreenPadding
import com.kproject.quotes.presentation.theme.PreviewTheme

@Composable
fun SignUpScreen(
    onNavigateToHomeScreen: () -> Unit,
    onNavigateBack: () -> Unit
) {
    MainContent(onNavigateBack = onNavigateBack)
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    val heightSpacing = 24.dp

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var usernameErrorMessage by remember { mutableStateOf("") }

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
            TextField(
                value = username,
                onValueChange = { newValue ->
                    username = newValue
                    usernameErrorMessage = if (username.length < 3) {
                        "Username too short"
                    } else {
                        ""
                    }
                },
                hint = stringResource(id = R.string.username),
                leadingIcon = R.drawable.outline_person_24,
                errorMessage = usernameErrorMessage
            )
            Spacer(Modifier.height(heightSpacing))
            AuthButton(
                text = stringResource(id = R.string.signup),
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewTheme {
        SignUpScreen(
            onNavigateToHomeScreen = {},
            onNavigateBack = {}
        )
    }
}