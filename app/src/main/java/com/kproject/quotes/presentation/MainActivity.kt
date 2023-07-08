package com.kproject.quotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.quotes.R
import com.kproject.quotes.presentation.screens.components.PostBottomSheet
import com.kproject.quotes.presentation.theme.QuotesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // NavigationGraph()
                    // CustomPagination()
                    Test()
                }
            }
        }
    }
}

@Composable
private fun Test(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var titleFieldErrorMessage by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var authorFieldErrorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                coroutineScope.launch {
                    showBottomSheet = true
                }
            },
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2E2E2E)
            ),
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "Show BottomSheet",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }

    PostBottomSheet(
        showBottomSheet = showBottomSheet,
        onDismiss = { showBottomSheet = false},
        bottomSheetTitle = stringResource(id = R.string.post_quote),
        bottomSheetButtonTitle = "Post",
        onButtonClick = {},
        quoteTitle = title,
        onQuoteTitleChange = {
            title = it
            titleFieldErrorMessage = if (it.length < 7 || it.length > 1000) {
                context.getString(R.string.error_quote_text_allowed_characters)
            } else {
                ""
            }
        },
        quoteTitleFieldErrorMessage = titleFieldErrorMessage,
        quoteAuthor = author,
        onQuoteAuthorChange = {
            author = it
            authorFieldErrorMessage = if (it.isBlank() || it.length > 80) {
                context.getString(R.string.error_quote_author_allowed_characters)
            } else {
                ""
            }
        },
        quoteAuthorFieldErrorMessage = authorFieldErrorMessage
    )
}