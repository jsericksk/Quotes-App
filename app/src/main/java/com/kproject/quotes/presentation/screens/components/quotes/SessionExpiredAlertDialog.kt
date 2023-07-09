package com.kproject.quotes.presentation.screens.components.quotes

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kproject.quotes.R
import com.kproject.quotes.presentation.screens.components.SimpleAlertDialog

@Composable
fun SessionExpiredAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,
) {
    SimpleAlertDialog(
        showDialog = showDialog,
        onDismiss = onDismiss,
        iconResId = R.drawable.outline_error_outline_24,
        title = stringResource(id = R.string.expired_login_session),
        message = stringResource(id = R.string.expired_login_session_message),
        onClickButtonOk = onNavigateToLoginScreen,
        cancelable = false,
        showButtonCancel = false
    )
}