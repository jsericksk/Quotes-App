package com.kproject.quotes.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kproject.quotes.R
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.screens.components.CustomSearchBar
import com.kproject.quotes.presentation.screens.components.QuotesList

@Composable
fun HomeScreen(
    onNavigateToLoginScreen: () -> Unit,
    onNavigateToUserProfileScreen: () -> Unit,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val quotes = homeViewModel.getQuotes().collectAsLazyPagingItems()

    MainContent(
        uiState = uiState,
        quotes = quotes,
        onSearchQueryChange = homeViewModel::onSearchQueryChange,
        onNavigateToUserProfileScreen = onNavigateToUserProfileScreen
    )
}

@Composable
private fun MainContent(
    uiState: HomeUiState,
    quotes: LazyPagingItems<Quote>,
    onSearchQueryChange: (String) -> Unit,
    onNavigateToUserProfileScreen: () -> Unit,
) {
    Scaffold(
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
            CustomSearchBar(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange,
                onSearch = {},
                trailingIcon = {
                    IconButton(onClick = onNavigateToUserProfileScreen) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_person_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    }
                },
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            )

            QuotesList(
                quotes = quotes,
            )
        }
    }
}