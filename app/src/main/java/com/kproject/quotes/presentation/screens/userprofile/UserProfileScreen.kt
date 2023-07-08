package com.kproject.quotes.presentation.screens.userprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kproject.quotes.R
import com.kproject.quotes.presentation.model.PostQuote
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.screens.components.CenterTopBar
import com.kproject.quotes.presentation.screens.components.ProgressAlertDialog
import com.kproject.quotes.presentation.screens.components.SimpleAlertDialog
import com.kproject.quotes.presentation.screens.components.quotes.PostBottomSheet
import com.kproject.quotes.presentation.screens.components.quotes.QuotesList
import com.kproject.quotes.presentation.utils.Utils

@Composable
fun UserProfileScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val quotes = userProfileViewModel.quotes.collectAsLazyPagingItems()

    var quoteToModify: Quote? by remember { mutableStateOf(null) }
    var showDeleteQuoteDialog by remember { mutableStateOf(false) }
    var showDeleteQuoteProgressDialog by remember { mutableStateOf(false) }

    var showPostBottomSheet by remember { mutableStateOf(false) }
    var showUpdatingQuoteProgressDialog by remember { mutableStateOf(false) }

    MainContent(
        quotes = quotes,
        onEditQuote = { quote ->
            quoteToModify = quote
            showPostBottomSheet = true
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
                    onSuccess = {
                        showDeleteQuoteProgressDialog = false
                        quotes.refresh()
                        Utils.showToast(
                            context = context,
                            message = context.getString(R.string.quote_deleted_successfully)
                        )
                    },
                    onError = {
                        showDeleteQuoteProgressDialog = false
                        quotes.refresh()
                        Utils.showToast(
                            context = context,
                            message = context.getString(R.string.error_deleting_quote)
                        )
                    }
                )
            }
        },
    )

    ProgressAlertDialog(
        showDialog = showDeleteQuoteProgressDialog,
        title = stringResource(id = R.string.deleting_quote)
    )

    quoteToModify?.let { quoteToUpdate ->
        PostBottomSheet(
            showBottomSheet = showPostBottomSheet,
            onDismiss = { showPostBottomSheet = false },
            bottomSheetTitle = stringResource(id = R.string.update_quote),
            bottomSheetButtonTitle = stringResource(id = R.string.update),
            defaultPostQuote = PostQuote(
                quote = quoteToUpdate.quote,
                author = quoteToUpdate.author
            ),
            quoteInputValidationUseCase = userProfileViewModel.quoteInputValidationUseCase,
            onButtonClick = { postQuote ->
                showUpdatingQuoteProgressDialog = true
                userProfileViewModel.editQuote(
                    quoteId = quoteToUpdate.id,
                    postQuote = postQuote,
                    onSuccess = {
                        showUpdatingQuoteProgressDialog = false
                        quotes.refresh()
                        Utils.showToast(
                            context = context,
                            message = context.getString(R.string.quote_updated_successfully)
                        )
                    },
                    onError = {
                        showUpdatingQuoteProgressDialog = false
                        Utils.showToast(
                            context = context,
                            message = context.getString(R.string.error_updating_quote)
                        )
                    }
                )
            }
        )
    }

    ProgressAlertDialog(
        showDialog = showUpdatingQuoteProgressDialog,
        title = stringResource(id = R.string.updating_quote)
    )
}

@Composable
private fun MainContent(
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
