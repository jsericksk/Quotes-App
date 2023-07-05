package com.kproject.quotes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.kproject.quotes.domain.repository.QuotesRepository
import com.kproject.quotes.presentation.model.fromModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        getQuotes()
    }

    private fun getQuotes() {
        viewModelScope.launch {
            quotesRepository
                .getAllQuotes(filter = _uiState.value.searchQuery)
                .cachedIn(viewModelScope).collect { pagingDataQuoteModel ->
                    _uiState.update {
                        val quotes = pagingDataQuoteModel.map { quoteModel ->
                            quoteModel.fromModel()
                        }
                        it.copy(quotes = quotes)
                    }
                }
        }
    }
}