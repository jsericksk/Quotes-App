package com.kproject.quotes.presentation.screens.userprofile

import androidx.paging.PagingData
import com.kproject.quotes.presentation.model.Quote

data class UserProfileUiState(
    val searchQuery: String = "",
    val quotes: PagingData<Quote> = PagingData.empty(),
)