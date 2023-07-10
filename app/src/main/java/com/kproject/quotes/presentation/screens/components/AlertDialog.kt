package com.kproject.quotes.presentation.screens.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kproject.quotes.R
import com.kproject.quotes.presentation.theme.CompletePreview
import com.kproject.quotes.presentation.theme.PreviewTheme

val defaultDialogPadding = 24.dp

@Composable
fun SimpleAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    @DrawableRes iconResId: Int? = null,
    title: String? = null,
    message: String,
    cancelable: Boolean = true,
    okButtonEnabled: Boolean = true,
    showButtonCancel: Boolean = true,
    okButtonTitle: String = stringResource(id = R.string.button_ok),
    cancelButtonTitle: String = stringResource(id = R.string.button_cancel),
    shape: Shape = MaterialTheme.shapes.extraLarge,
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit = {}
) {
    CustomAlertDialog(
        showDialog = showDialog,
        onDismiss = onDismiss,
        iconResId = iconResId,
        title = title,
        cancelable = cancelable,
        okButtonEnabled = okButtonEnabled,
        showButtonCancel = showButtonCancel,
        okButtonTitle = okButtonTitle,
        cancelButtonTitle = cancelButtonTitle,
        shape = shape,
        onClickButtonOk = onClickButtonOk,
        onClickButtonCancel = onClickButtonCancel
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ProgressAlertDialog(showDialog: Boolean, title: String? = null) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {},
            content = {
                if (title != null) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .padding(defaultDialogPadding)
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun CustomAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    @DrawableRes iconResId: Int? = null,
    title: String? = null,
    cancelable: Boolean = true,
    okButtonEnabled: Boolean = true,
    showButtonCancel: Boolean = true,
    okButtonTitle: String = stringResource(id = R.string.button_ok),
    cancelButtonTitle: String = stringResource(id = R.string.button_cancel),
    shape: Shape = MaterialTheme.shapes.extraLarge,
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit = {},
    showActionButtons: Boolean = true,
    content: @Composable () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { if (cancelable) onDismiss.invoke() },
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = shape
                        )
                        .padding(
                            start = defaultDialogPadding,
                            end = defaultDialogPadding,
                            top = defaultDialogPadding,
                            bottom = 14.dp
                        )
                ) {
                    iconResId?.let {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = iconResId),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary.copy(0.7f),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Spacer(Modifier.height(8.dp))
                    }

                    // Title
                    title?.let {
                        DialogTitle(title = title)
                        Spacer(Modifier.height(22.dp))
                    }

                    // Content
                    Box(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(1f, fill = false)
                    ) {
                        content.invoke()
                    }

                    Spacer(Modifier.height(20.dp))

                    if (showActionButtons) {
                        DialogActionButtons(
                            onDismiss = onDismiss,
                            okButtonEnabled = okButtonEnabled,
                            showButtonCancel = showButtonCancel,
                            okButtonTitle = okButtonTitle,
                            cancelButtonTitle = cancelButtonTitle,
                            onClickButtonOk = onClickButtonOk,
                            onClickButtonCancel = onClickButtonCancel
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun ColumnScope.DialogTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        modifier = Modifier.align(Alignment.CenterHorizontally),
    )
}

@Composable
fun ColumnScope.DialogActionButtons(
    onDismiss: () -> Unit,
    okButtonEnabled: Boolean,
    showButtonCancel: Boolean,
    okButtonTitle: String,
    cancelButtonTitle: String,
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit
) {
    Row(
        modifier = Modifier.align(Alignment.End)
    ) {
        if (showButtonCancel) {
            TextButton(
                onClick = {
                    onDismiss.invoke()
                    onClickButtonCancel.invoke()
                }
            ) {
                Text(
                    text = cancelButtonTitle,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.width(6.dp))
        }
        TextButton(
            onClick = {
                onDismiss.invoke()
                onClickButtonOk.invoke()
            },
            enabled = okButtonEnabled
        ) {
            Text(
                text = okButtonTitle,
                color = if (okButtonEnabled) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                },
                fontSize = 16.sp
            )
        }
    }
}

@CompletePreview
@Composable
private fun Preview() {
    PreviewTheme {
        SimpleAlertDialog(
            showDialog = true,
            onDismiss = {},
            title = "App Theme",
            message = "Select the application theme",
            onClickButtonOk = {}
        )
    }
}