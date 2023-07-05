package com.kproject.quotes.presentation.screens.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kproject.quotes.R
import com.kproject.quotes.presentation.theme.PreviewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit = {},
    trailingIcon: @Composable () -> Unit = {}
) {
    var isActive by rememberSaveable { mutableStateOf(false) }
    SearchBar(
        query = query,
        onQueryChange = { value ->
            onQueryChange.invoke(value)
        },
        onSearch = { value ->
            isActive = false
            onActiveChange.invoke(false)
            onSearch.invoke(value)
        },
        active = isActive,
        onActiveChange = { active ->
            isActive = active
            onActiveChange.invoke(active)
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_quotes),
                color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
            )
        },
        leadingIcon = {
            val icon = if (isActive) {
                Icons.Outlined.ArrowBack
            } else {
                Icons.Outlined.Search
            }
            IconButton(
                onClick = {
                    if (query.isNotEmpty()) {
                        onQueryChange.invoke("")
                    } else {
                        isActive = false
                    }
                },
                enabled = isActive
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
            if (isActive) {
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
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    ) {

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
            onActiveChange = {}
        )
    }
}