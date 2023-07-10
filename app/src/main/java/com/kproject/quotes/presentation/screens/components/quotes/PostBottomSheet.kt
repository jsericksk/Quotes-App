package com.kproject.quotes.presentation.screens.components.quotes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.quotes.R
import com.kproject.quotes.commom.validation.QuoteValidationState
import com.kproject.quotes.domain.usecase.quotes.validation.QuoteInputValidationUseCase
import com.kproject.quotes.presentation.model.PostQuote
import com.kproject.quotes.presentation.utils.UiText
import com.kproject.quotes.presentation.utils.toErrorMessage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostBottomSheet(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    bottomSheetTitle: String,
    bottomSheetButtonTitle: String,
    defaultPostQuote: PostQuote,
    quoteInputValidationUseCase: QuoteInputValidationUseCase,
    onButtonClick: (PostQuote) -> Unit
) {
    if (showBottomSheet) {
        val bottomSheetState = rememberModalBottomSheetState()
        val coroutineScope = rememberCoroutineScope()
        val postBottomSheetState = rememberPostBottomSheetState(
            postQuote = defaultPostQuote,
            quoteInputValidationUseCase = quoteInputValidationUseCase
        )

        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 12.dp,
                    bottom = 24.dp,
                    start = 24.dp,
                    end = 24.dp
                )
            ) {
                Text(
                    text = bottomSheetTitle,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(16.dp))

                TextField(
                    value = postBottomSheetState.quoteText,
                    onValueChange = { value ->
                        postBottomSheetState.onQuoteTextChange(value)
                    },
                    hint = stringResource(id = R.string.quote_text),
                    errorMessage = postBottomSheetState.quoteTextErrorMessage.asString(),
                    modifier = Modifier.height(200.dp)
                )

                Spacer(Modifier.height(8.dp))

                TextField(
                    value = postBottomSheetState.quoteAuthor,
                    onValueChange = { value ->
                        postBottomSheetState.onQuoteAuthorChange(value)
                    },
                    hint = stringResource(id = R.string.quote_author),
                    singleLine = true,
                    errorMessage = postBottomSheetState.quoteAuthorErrorMessage.asString(),
                )

                Spacer(Modifier.height(18.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (!postBottomSheetState.hasValidationError()) {
                                val postQuote = PostQuote(
                                    quote = postBottomSheetState.quoteText,
                                    author = postBottomSheetState.quoteAuthor
                                )
                                onButtonClick.invoke(postQuote)
                                bottomSheetState.hide()
                                onDismiss.invoke()
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                ) {
                    Text(
                        text = bottomSheetButtonTitle,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    errorMessage: String,
    singleLine: Boolean = false,
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange.invoke(newValue)
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            ),
            label = {
                Text(text = hint)
            },
            shape = MaterialTheme.shapes.medium,
            isError = errorMessage.isNotEmpty(),
            singleLine = singleLine,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                focusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(0.7f),
                errorContainerColor = Color.Transparent,
                errorBorderColor = MaterialTheme.colorScheme.errorContainer.copy(0.7f),
                errorLabelColor = MaterialTheme.colorScheme.errorContainer,
                errorCursorColor = MaterialTheme.colorScheme.errorContainer,
                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                    backgroundColor = MaterialTheme.colorScheme.background
                )
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

private class PostBottomSheetState(
    postQuote: PostQuote,
    private val quoteInputValidationUseCase: QuoteInputValidationUseCase
) {
    var quoteText: String by mutableStateOf(postQuote.quote)
        private set
    var quoteAuthor: String by mutableStateOf(postQuote.author)
        private set
    var quoteTextErrorMessage: UiText by mutableStateOf(UiText.HardcodedString(""))
        private set
    var quoteAuthorErrorMessage: UiText by mutableStateOf(UiText.HardcodedString(""))
        private set

    private var validateFieldsWhenTyping: Boolean = false

    fun onQuoteTextChange(text: String) {
        quoteText = text
        validateFieldsWhenTyping()
    }

    fun onQuoteAuthorChange(author: String) {
        quoteAuthor = author
        validateFieldsWhenTyping()
    }

    fun hasValidationError(): Boolean {
        validateFieldsWhenTyping = true
        val validationState =
                quoteInputValidationUseCase(quote = quoteText, author = quoteAuthor)
        val errorMessage = validationState.toErrorMessage()
        quoteTextErrorMessage = if (validationState == QuoteValidationState.QuoteTextInvalid) {
            errorMessage
        } else {
            UiText.HardcodedString("")
        }
        quoteAuthorErrorMessage = if (validationState == QuoteValidationState.QuoteAuthorInvalid) {
            errorMessage
        } else {
            UiText.HardcodedString("")
        }
        return (validationState != QuoteValidationState.Success)
    }

    private fun validateFieldsWhenTyping() {
        if (validateFieldsWhenTyping) {
            hasValidationError()
        }
    }
}

@Composable
private fun rememberPostBottomSheetState(
    postQuote: PostQuote,
    quoteInputValidationUseCase: QuoteInputValidationUseCase
): PostBottomSheetState {
    return remember {
        PostBottomSheetState(postQuote, quoteInputValidationUseCase)
    }
}

