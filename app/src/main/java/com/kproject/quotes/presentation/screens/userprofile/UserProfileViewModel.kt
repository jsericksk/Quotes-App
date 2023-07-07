package com.kproject.quotes.presentation.screens.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.data.fromJson
import com.kproject.quotes.data.toJson
import com.kproject.quotes.domain.model.LoggedInUserModel
import com.kproject.quotes.domain.repository.QuotesRepository
import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCase
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.model.fromModel
import com.kproject.quotes.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val getPreferenceUseCase: GetPreferenceUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> get() = _uiState

    var quotes: Flow<PagingData<Quote>>

    init {
        val loggedInUserModel = getPreferenceUseCase(
            key = PrefsConstants.LoggedInUserInfo,
            defaultValue = LoggedInUserModel().toJson()
        ).fromJson(LoggedInUserModel::class.java)
        quotes = quotesRepository.getQuotesFromUserId(
            filter = _uiState.value.searchQuery,
            userId = loggedInUserModel.userId
        ).map { pagingDataModel ->
            pagingDataModel.map { quoteModel -> quoteModel.fromModel() }
        }.cachedIn(viewModelScope)
    }

    fun editQuote(quote: Quote, onResult: (error: Boolean) -> Unit) {
        viewModelScope.launch {
            val result = quotesRepository.updateById(quote.toModel())
            result.collect { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        onResult.invoke(false)
                    }
                    is ResultState.Error -> {
                        onResult.invoke(true)
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteQuote(quote: Quote, onResult: (error: Boolean) -> Unit) {
        viewModelScope.launch {
            val result = quotesRepository.deleteById(quote.id)
            result.collect { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                       onResult.invoke(false)
                    }
                    is ResultState.Error -> {
                        onResult.invoke(true)
                    }
                    else -> {}
                }
            }
        }
    }
}