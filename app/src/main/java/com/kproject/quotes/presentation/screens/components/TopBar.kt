package com.kproject.quotes.presentation.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kproject.quotes.R
import com.kproject.quotes.presentation.theme.PreviewTheme
import com.kproject.quotes.presentation.theme.SimplePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: ImageVector? = null,
    navigationIconClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = navigationIconClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
    )
}

@SimplePreview
@Composable
private fun Preview() {
    PreviewTheme {
        Column {
            CenterTopBar(
                title = stringResource(id = R.string.app_name),
                navigationIcon = Icons.Default.Menu
            )
            Spacer(Modifier.height(24.dp))
            CenterTopBar(
                title = stringResource(id = R.string.app_name),
                navigationIcon = Icons.Default.Menu,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    }
}