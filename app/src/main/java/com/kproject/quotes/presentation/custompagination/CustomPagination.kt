package com.kproject.quotes.presentation.custompagination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.model.fakeQuotesList

@Composable
fun CustomPagination(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MyList()
    }
}

@Composable
private fun MyList() {
    val listState = rememberLazyListState()
    val isScrollToEnd by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }
    val isAtBottom = !listState.canScrollForward
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = fakeQuotesList) { quote ->
            MyListItem(quote = quote)
        }

        if (isAtBottom) {
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
    }
}

@Composable
private fun MyListItem(
    quote: Quote
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF232424)
        ),
        modifier = Modifier.padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = quote.quote,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 16.sp
            )
            Text(
                text = quote.author,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 16.sp
            )
        }
    }
}