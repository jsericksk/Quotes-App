package com.kproject.quotes.presentation.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.quotes.presentation.theme.PreviewTheme
import com.kproject.quotes.presentation.theme.SimplePreview

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@SimplePreview
@Composable
private fun Preview() {
    PreviewTheme {
        Column {
            AuthButton(
                text = "Login",
                onClick = {}
            )
        }
    }
}