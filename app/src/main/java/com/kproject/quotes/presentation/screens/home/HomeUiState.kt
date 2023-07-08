package com.kproject.quotes.presentation.screens.home

import com.kproject.quotes.presentation.screens.components.quotes.PostQuoteUiState

data class HomeUiState(
    val searchQuery: String = "",
    val loggedInUsername: String = "",
    val quoteUiState: PostQuoteUiState = PostQuoteUiState()
)