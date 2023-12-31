package com.kproject.quotes.presentation.screens.components.quotes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.kproject.quotes.R
import com.kproject.quotes.commom.exception.QuoteException
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.screens.components.EmptyListInfo
import com.kproject.quotes.presentation.screens.components.ProgressIndicator
import com.kproject.quotes.presentation.utils.Utils
import com.kproject.quotes.presentation.utils.toQuoteErrorMessage

@Composable
fun QuotesList(
    modifier: Modifier = Modifier,
    quotes: LazyPagingItems<Quote>,
    showActionOptions: Boolean,
    onEditQuote: (Quote) -> Unit = {},
    onDeleteQuote: (Quote) -> Unit = {},
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        when (val state = quotes.loadState.refresh) {
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
                            onClick = {},
                            showActionOptions = showActionOptions,
                            onEditQuote = onEditQuote,
                            onDeleteQuote = onDeleteQuote
                        )
                    }
                }
            }
            is LoadState.Error -> {
                item {
                    val quoteException = remember(state.error) {
                        (state.error as QuoteException)
                    }
                    val errorMessage = remember(quoteException) {
                        quoteException.toQuoteErrorMessage()
                    }
                    val showRetryButton = remember(quoteException) {
                        val isNotEmptyQuotes = (quoteException != QuoteException.UserWithoutPosts
                                && quoteException != QuoteException.NoQuoteFound)
                        isNotEmptyQuotes
                    }
                    val infoIconResId = remember(quoteException) {
                        if (quoteException == QuoteException.UserWithoutPosts) {
                            R.drawable.outline_format_quote_24
                        } else {
                            R.drawable.outline_error_outline_24
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillParentMaxSize()
                    ) {
                        EmptyListInfo(
                            iconResId = infoIconResId,
                            title = errorMessage.asString()
                        )
                        if (showRetryButton) {
                            Spacer(Modifier.height(8.dp))
                            RetryButton(
                                onClick = { quotes.retry() },
                                modifier = Modifier.padding(8.dp)
                            )
                        }
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
    onClick: () -> Unit,
    showActionOptions: Boolean,
    onEditQuote: (Quote) -> Unit,
    onDeleteQuote: (Quote) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick.invoke() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f)
            ) {
                Column {
                    Text(
                        text = "“ " + quote.quote,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(6.dp))
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
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

                    QuoteCardActionButtons(quote = quote)
                }
            }

            if (showActionOptions) {
                var showOptions by remember { mutableStateOf(false) }
                Box {
                    IconButton(onClick = { showOptions = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    ActionOptions(
                        showOptions = showOptions,
                        onDismiss = { showOptions = false },
                        onEditQuote = {
                            onEditQuote.invoke(quote)
                        },
                        onDeleteQuote = {
                            onDeleteQuote.invoke(quote)
                        }
                    )
                }
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
private fun ActionOptions(
    showOptions: Boolean,
    onDismiss: () -> Unit,
    onEditQuote: () -> Unit,
    onDeleteQuote: () -> Unit,
) {
    DropdownMenu(
        expanded = showOptions,
        onDismissRequest = onDismiss,
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        DropdownMenuItem(
            onClick = {
                onDismiss.invoke()
                onEditQuote.invoke()
            },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_edit_24),
                    contentDescription = "Edit quote",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.edit_quote),
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
        )

        DropdownMenuItem(
            onClick = {
                onDismiss.invoke()
                onDeleteQuote.invoke()
            },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_delete_24),
                    contentDescription = "Delete quote",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.delete_quote),
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
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
                containerColor = MaterialTheme.colorScheme.primary.copy(0.8f)
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
                containerColor = MaterialTheme.colorScheme.primary.copy(0.8f)
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