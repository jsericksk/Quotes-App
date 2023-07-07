package com.kproject.quotes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.data.fromJson
import com.kproject.quotes.data.toJson
import com.kproject.quotes.domain.model.LoggedInUserModel
import com.kproject.quotes.domain.repository.QuotesRepository
import com.kproject.quotes.domain.usecase.auth.LogoutUseCase
import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCase
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.model.fromModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val getPreferenceUseCase: GetPreferenceUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    val quotes: Flow<PagingData<Quote>> = quotesRepository.getAllQuotes(
        filter = _uiState.value.searchQuery
    ).map { pagingDataModel ->
        pagingDataModel.map { quoteModel -> quoteModel.fromModel() }
    }.cachedIn(viewModelScope)

    init {
        getLoggedInUser()
    }

    private fun getLoggedInUser() {
        val loggedInUserModel = getPreferenceUseCase(
            key = PrefsConstants.LoggedInUserInfo,
            defaultValue = LoggedInUserModel().toJson()
        ).fromJson(LoggedInUserModel::class.java)
        _uiState.update {
            it.copy(loggedInUsername = loggedInUserModel.username)
        }
    }

    fun onSearchQueryChange(searchQuery: String) {
        _uiState.update {
            it.copy(searchQuery = searchQuery)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}