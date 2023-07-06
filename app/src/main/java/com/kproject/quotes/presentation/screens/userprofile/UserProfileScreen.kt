package com.kproject.quotes.presentation.screens.userprofile

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.kproject.quotes.presentation.screens.components.EmptyListInfo
import com.kproject.quotes.presentation.screens.components.ProgressIndicator
import com.kproject.quotes.presentation.utils.Utils

@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit
) {
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val uiState by userProfileViewModel.uiState.collectAsStateWithLifecycle()
    val quotes = userProfileViewModel.getQuotesFromUser().collectAsLazyPagingItems()

    QuotesList(
        uiState = uiState,
        quotes = quotes,
    )
}

@Composable
private fun QuotesList(
    modifier: Modifier = Modifier,
    uiState: UserProfileUiState,
    quotes: LazyPagingItems<Quote>,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        when (quotes.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    ProgressIndicator(
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
            is LoadState.NotLoading -> {
                items(count = quotes.itemCount) { index ->
                    val quote = quotes[index]
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
                            onClick = { quotes.retry() },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

        when (quotes.loadState.append) {
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
                            onClick = { quotes.retry() },
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
            containerColor = Color(0xFF0F202E)
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

                QuoteCardActionButtons(quote = quote)
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
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.try_again),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun QuoteCardActionButtons(
    modifier: Modifier = Modifier,
    quote: Quote
) {
    val context = LocalContext.current
    val quoteAndAuthor = "${quote.quote} — ${quote.author}"
    Row(modifier = modifier) {
        IconButton(
            onClick = {
                Utils.copyToClipBoard(context = context, text = quoteAndAuthor)
            },
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
            onClick = {
                Utils.shareText(context = context, text = quoteAndAuthor)
            },
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