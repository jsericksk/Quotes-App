package com.kproject.quotes.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.kproject.quotes.R
import com.kproject.quotes.presentation.model.PostQuote
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.screens.components.CustomAlertDialog
import com.kproject.quotes.presentation.screens.components.CustomSearchBar
import com.kproject.quotes.presentation.screens.components.ProgressAlertDialog
import com.kproject.quotes.presentation.screens.components.SimpleAlertDialog
import com.kproject.quotes.presentation.screens.components.quotes.PostBottomSheet
import com.kproject.quotes.presentation.screens.components.quotes.QuotesList
import com.kproject.quotes.presentation.theme.PreviewTheme
import com.kproject.quotes.presentation.utils.Utils

@Composable
fun HomeScreen(
    onNavigateToUserProfileScreen: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,
) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val quotes = homeViewModel.quotes.collectAsLazyPagingItems()

    var showPostBottomSheet by remember { mutableStateOf(false) }

    MainContent(
        uiState = uiState,
        quotes = quotes,
        onSearchQueryChange = homeViewModel::onSearchQueryChange,
        onSearchQuote = {
            homeViewModel.searchQuote()
        },
        onClearSearch = {
            homeViewModel.clearSearchResults()
        },
        onNavigateToUserProfileScreen = onNavigateToUserProfileScreen,
        onPostQuote = { showPostBottomSheet = true },
        onLogout = {
            homeViewModel.logout()
            onNavigateToLoginScreen.invoke()
        }
    )

    var showPostingQuoteProgressDialog by remember { mutableStateOf(false) }

    PostBottomSheet(
        showBottomSheet = showPostBottomSheet,
        onDismiss = { showPostBottomSheet = false },
        bottomSheetTitle = stringResource(id = R.string.post_quote),
        bottomSheetButtonTitle = stringResource(id = R.string.post),
        defaultPostQuote = PostQuote(),
        quoteInputValidationUseCase = homeViewModel.quoteInputValidationUseCase,
        onButtonClick = { postQuote ->
            showPostingQuoteProgressDialog = true
            homeViewModel.postQuote(
                postQuote = postQuote,
                onSuccess = { quote ->
                    showPostingQuoteProgressDialog = false
                    quotes.refresh()
                    Utils.showToast(
                        context = context,
                        message = context.getString(R.string.quote_posted_successfully)
                    )
                },
                onError = {
                    showPostingQuoteProgressDialog = false
                    Utils.showToast(
                        context = context,
                        message = context.getString(R.string.error_posting_quote)
                    )
                }
            )
        }
    )

    ProgressAlertDialog(
        showDialog = showPostingQuoteProgressDialog,
        title = stringResource(id = R.string.posting_quote)
    )
}

@Composable
private fun MainContent(
    uiState: HomeUiState,
    quotes: LazyPagingItems<Quote>,
    onSearchQueryChange: (String) -> Unit,
    onSearchQuote: (String) -> Unit,
    onClearSearch: () -> Unit,
    onNavigateToUserProfileScreen: () -> Unit,
    onLogout: () -> Unit,
    onPostQuote: () -> Unit,
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showLogoutConfirmationDialog by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onPostQuote,
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
                onSearch = onSearchQuote,
                onSearchActive = { isActive ->
                    if (!isActive) {
                        // onClearSearch.invoke()
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { showLogoutDialog = true }) {
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
                    .padding(12.dp)
                    .fillMaxWidth()
            )

            QuotesList(
                quotes = quotes,
                showActionOptions = false,
            )
        }

        UserProfileDialog(
            username = uiState.loggedInUsername,
            showDialog = showLogoutDialog,
            onDismiss = { showLogoutDialog = false },
            onViewUserProfile = onNavigateToUserProfileScreen,
            onLogout = { showLogoutConfirmationDialog = true }
        )

        SimpleAlertDialog(
            showDialog = showLogoutConfirmationDialog,
            onDismiss = { showLogoutConfirmationDialog = false },
            iconResId = R.drawable.outline_logout_24,
            title = stringResource(id = R.string.logout),
            message = stringResource(id = R.string.logout_confirmation),
            onClickButtonOk = onLogout,
        )
    }
}

@Composable
private fun UserProfileDialog(
    username: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onViewUserProfile: () -> Unit,
    onLogout: () -> Unit,
) {
    CustomAlertDialog(
        showDialog = showDialog,
        onDismiss = onDismiss,
        onClickButtonOk = {},
        showActionButtons = false
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_person_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(6.dp)
                )
                Text(
                    text = username,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    onDismiss.invoke()
                    onViewUserProfile.invoke()
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_format_quote_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(6.dp)
                )
                Text(
                    text = stringResource(id = R.string.my_quotes),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(Modifier.height(18.dp))
            Button(
                onClick = {
                    onDismiss.invoke()
                    onLogout.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.logout),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewTheme {
        UserProfileDialog(
            username = "John",
            showDialog = true,
            onDismiss = { },
            onViewUserProfile = {},
            onLogout = {}
        )
    }
}