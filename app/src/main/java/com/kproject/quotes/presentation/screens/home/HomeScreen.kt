package com.kproject.quotes.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kproject.quotes.R
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.screens.components.CenterTopBar
import com.kproject.quotes.presentation.screens.components.EmptyListInfo
import com.kproject.quotes.presentation.screens.components.ProgressIndicator

@Composable
fun HomeScreen(
    onNavigateToLoginScreen: () -> Unit,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val quotes = homeViewModel.getQuotes().collectAsLazyPagingItems()

    MainContent(
        uiState = uiState,
        quotesList = quotes,
    )
}

@Composable
private fun MainContent(
    uiState: HomeUiState,
    quotesList: LazyPagingItems<Quote>,
) {
    Scaffold(
        topBar = {
            CenterTopBar(
                title = stringResource(id = R.string.app_name),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_settings_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
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
                uiState = uiState,
                quotesList = quotesList,
            )
        }
    }
}

@Composable
private fun QuotesList(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    quotesList: LazyPagingItems<Quote>,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when (quotesList.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    ProgressIndicator(
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
            is LoadState.NotLoading -> {
                items(count = quotesList.itemCount) { index ->
                    val quote = quotesList[index]
                    quote?.let {
                        QuotesListItem(
                            quote = quote,
                            onClick = {}
                        )
                    }
                }
            }
            is LoadState.Error -> {
                item {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillParentMaxSize()
                    ) {
                        EmptyListInfo(
                            iconResId = R.drawable.outline_error_outline_24,
                            title = stringResource(id = R.string.error_loading_quotes)
                        )
                        Spacer(Modifier.height(8.dp))
                        RetryButton(
                            onClick = { quotesList.retry() },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

        when (quotesList.loadState.append) {
            is LoadState.Loading -> {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
            is LoadState.Error -> {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.error_loading_more_quotes_from_pagination),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(Modifier.height(4.dp))
                        RetryButton(
                            onClick = { quotesList.retry() },
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
private fun QuotesListItem(
    modifier: Modifier = Modifier,
    quote: Quote,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF01579B)
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick.invoke() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Column {
                Text(
                    text = "\" " + quote.quote,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 8,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "— " + quote.author,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_person_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(6.dp))
                Column {
                    Text(
                        text = quote.postedByUsername,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = quote.formattedDate,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                QuoteCardActionButtons()
            }
        }
    }
}

@Composable
private fun RetryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(
            text = stringResource(id = R.string.try_again),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun QuoteCardActionButtons(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        IconButton(
            onClick = {},
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_content_copy_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(Modifier.width(6.dp))
        IconButton(
            onClick = {},
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_share_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
