package com.kproject.quotes.presentation.screens.userprofile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.kproject.quotes.presentation.screens.components.QuotesList

@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit
) {
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val uiState by userProfileViewModel.uiState.collectAsStateWithLifecycle()
    val quotes = userProfileViewModel.getQuotesFromUser().collectAsLazyPagingItems()

    QuotesList(
        quotes = quotes,
    )
}
