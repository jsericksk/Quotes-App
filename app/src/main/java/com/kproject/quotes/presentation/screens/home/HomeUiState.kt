package com.kproject.quotes.presentation.screens.home

import androidx.paging.PagingData
import com.kproject.quotes.presentation.model.Quote

data class HomeUiState(
    val searchQuery: String = "",
    val searchedQuotes: PagingData<Quote> = PagingData.empty(),
    val loggedInUsername: String = ""
)