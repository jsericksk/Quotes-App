package com.kproject.quotes.presentation.screens.userprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kproject.quotes.R
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.screens.components.CenterTopBar
import com.kproject.quotes.presentation.screens.components.ProgressAlertDialog
import com.kproject.quotes.presentation.screens.components.QuotesList
import com.kproject.quotes.presentation.screens.components.SimpleAlertDialog
import com.kproject.quotes.presentation.utils.Utils

@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val uiState by userProfileViewModel.uiState.collectAsStateWithLifecycle()
    val quotes = userProfileViewModel.getQuotesFromUser().collectAsLazyPagingItems()

    var quoteToModify: Quote? by remember { mutableStateOf(null) }
    var showDeleteQuoteDialog by remember { mutableStateOf(false) }
    var showDeleteQuoteProgressDialog by remember { mutableStateOf(false) }

    MainContent(
        uiState = uiState,
        quotes = quotes,
        onEditQuote = { quote ->
            quoteToModify = quote
        },
        onDeleteQuote = { quote ->
            quoteToModify = quote
            showDeleteQuoteDialog = true
        },
        onNavigateBack = onNavigateBack
    )

    SimpleAlertDialog(
        showDialog = showDeleteQuoteDialog,
        onDismiss = { showDeleteQuoteDialog = false },
        iconResId = R.drawable.outline_delete_24,
        title = stringResource(id = R.string.delete_quote),
        message = stringResource(id = R.string.delete_quote_confirmation),
        onClickButtonOk = {
            quoteToModify?.let { quoteToDelete ->
                showDeleteQuoteProgressDialog = true
                userProfileViewModel.deleteQuote(
                    quote = quoteToDelete,
                    onResult = { isError ->
                        showDeleteQuoteProgressDialog = false
                        val toastMessage = if (isError) {
                            context.getString(R.string.error_deleting_quote)
                        } else {
                            context.getString(R.string.quote_deleted_successfully)
                        }
                        Utils.showToast(context = context, message = toastMessage)
                    }
                )
            }
        },
    )

    ProgressAlertDialog(
        showDialog = showDeleteQuoteProgressDialog,
        title = stringResource(id = R.string.deleting_quote)
    )
}

@Composable
private fun MainContent(
    uiState: UserProfileUiState,
    quotes: LazyPagingItems<Quote>,
    onEditQuote: (Quote) -> Unit,
    onDeleteQuote: (Quote) -> Unit,
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
