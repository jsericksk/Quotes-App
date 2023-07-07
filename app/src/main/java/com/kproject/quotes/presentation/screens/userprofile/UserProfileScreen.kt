package com.kproject.quotes.presentation.screens.userprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kproject.quotes.R
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.screens.components.CenterTopBar
import com.kproject.quotes.presentation.screens.components.QuotesList

@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit
) {
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val uiState by userProfileViewModel.uiState.collectAsStateWithLifecycle()
    val quotes = userProfileViewModel.getQuotesFromUser().collectAsLazyPagingItems()

    MainContent(
        uiState = uiState,
        quotes = quotes,
        onDeleteQuote = {},
        onEditQuote = {},
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun MainContent(
    uiState: UserProfileUiState,
    quotes: LazyPagingItems<Quote>,
    onDeleteQuote: (Quote) -> Unit,
    onEditQuote: (Quote) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterTopBar(
                title = stringResource(id = R.string.my_quotes),
                navigationIcon = Icons.Filled.ArrowBack,
                navigationIconClick = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.secondary,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_add_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            QuotesList(
                quotes = quotes,
                showActionOptions = true,
                onEditQuote = onEditQuote,
                onDeleteQuote = onDeleteQuote,
            )
        }
    }
}
