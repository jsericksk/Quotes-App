package com.kproject.quotes.presentation.screens.auth.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.quotes.R
import com.kproject.quotes.presentation.theme.PreviewTheme
import com.kproject.quotes.presentation.theme.SimplePreview

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    errorMessage: String = "",
    @DrawableRes leadingIcon: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    fieldType: FieldType = FieldType.None
) {
    var passwordVisible by rememberSaveable { mutableStateOf(fieldType != FieldType.Password) }
    val visibilityIcon =
            if (passwordVisible) R.drawable.outline_visibility_off_24 else R.drawable.outline_visibility_24

    Column {
        TextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange.invoke(newValue)
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp
            ),
            label = {
                Text(text = hint)
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = leadingIcon),
                    contentDescription = null,
                )
            },
            trailingIcon = {
                if (fieldType == FieldType.Password) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = visibilityIcon),
                            contentDescription = null,
                        )
                    }
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (passwordVisible) keyboardType else KeyboardType.Password
            ),
            shape = MaterialTheme.shapes.small,
            singleLine = true,
            isError = errorMessage.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                focusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer.copy(0.5f),
                errorBorderColor = Color.Transparent,
                errorCursorColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                errorLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                errorTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                errorLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                    backgroundColor = MaterialTheme.colorScheme.background
                ),
            ),
            modifier = modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.errorContainer,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
    }
}

@SimplePreview
@Composable
private fun Preview() {
    PreviewTheme {
        Column {
            TextField(
                value = "",
                onValueChange = { },
                hint = "E-mail",
                leadingIcon = R.drawable.outline_email_24
            )
            Spacer(Modifier.height(20.dp))
            TextField(
                value = "123456",
                onValueChange = { },
                hint = "Password",
                leadingIcon = R.drawable.outline_key_24,
                fieldType = FieldType.Password
            )
            Spacer(Modifier.height(20.dp))
            TextField(
                value = "user/email.com",
                onValueChange = { },
                hint = "E-mail",
                leadingIcon = R.drawable.outline_email_24,
                errorMessage = "Invalid e-mail"
            )
        }
    }
}