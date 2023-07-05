package com.kproject.quotes.presentation.screens.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.kproject.quotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit = {}
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
                text = stringResource(id = R.string.search),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            if (isActive) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            if (query.isNotEmpty()) {
                                onQueryChange.invoke("")
                            } else {
                                isActive = false
                            }
                        }
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    ) {

    }
}