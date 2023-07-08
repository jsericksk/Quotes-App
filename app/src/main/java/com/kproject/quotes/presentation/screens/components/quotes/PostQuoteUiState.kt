package com.kproject.quotes.presentation.screens.components.quotes

import com.kproject.quotes.presentation.utils.UiText

data class PostQuoteUiState(
    val title: String = "",
    val titleError: UiText = UiText.HardcodedString(""),
    val author: String = "",
    val authorError: UiText = UiText.HardcodedString(""),
    val isLoading: Boolean = false,
    val postError: Boolean = false,
    val postErrorMessage: UiText = UiText.HardcodedString(""),
    val validateFieldsWhenTyping: Boolean = false
)