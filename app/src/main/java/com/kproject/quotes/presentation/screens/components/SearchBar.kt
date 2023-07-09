package com.kproject.quotes.presentation.screens.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.quotes.R
import com.kproject.quotes.presentation.theme.PreviewTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onSearchActive: (Boolean) -> Unit = {},
    trailingIcon: @Composable () -> Unit = {}
) {
    var isSearching by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isSearching) {
        onSearchActive.invoke(isSearching)
    }

    fun closeSearch(clearQuery: Boolean = true) {
        isSearching = false
        keyboardController?.hide()
        focusManager.clearFocus()
        if (clearQuery) {
            onQueryChange.invoke("")
        }
    }

    TextField(
        value = query,
        onValueChange = { value ->
            onQueryChange.invoke(value)
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 18.sp
        ),
        label = {
            Text(
                text = stringResource(id = R.string.search_quotes),
                color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
            )
        },
        leadingIcon = {
            val icon = if (isSearching) {
                Icons.Outlined.ArrowBack
            } else {
                Icons.Outlined.Search
            }
            IconButton(
                onClick = {
                    closeSearch()
                },
                enabled = isSearching
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(6.dp)
                )
            }
        },
        trailingIcon = {
            if (isSearching) {
                IconButton(
                    onClick = {
                        if (query.isNotEmpty()) {
                            onQueryChange.invoke("")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }
            } else {
                trailingIcon.invoke()
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch.invoke(query)
                closeSearch(clearQuery = false)
            }
        ),
        shape = CircleShape,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
            focusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.8f),
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.6f),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                backgroundColor = MaterialTheme.colorScheme.background
            ),
        ),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isSearching = focusState.hasFocus
            }
    )

    BackHandler(enabled = isSearching) {
        closeSearch()
    }
}

@Preview
@Composable
private fun Preview() {
    PreviewTheme {
        var query by remember { mutableStateOf("") }
        CustomSearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {},
            onSearchActive = {}
        )
    }
}