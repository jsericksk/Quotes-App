package com.kproject.quotes.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kproject.quotes.domain.repository.QuotesRepository
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.model.fromModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
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

    fun getQuotes(): Flow<PagingData<Quote>> {
        return quotesRepository.getAllQuotes(filter = _uiState.value.searchQuery)
            .cachedIn(viewModelScope).map { pagingDataModel ->
                pagingDataModel.map { quoteModel ->
                    quoteModel.fromModel()
                }
            }
    }
}