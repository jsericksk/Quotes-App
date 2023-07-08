package com.kproject.quotes.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.data.fromJson
import com.kproject.quotes.data.remote.model.quotes.toBody
import com.kproject.quotes.data.toJson
import com.kproject.quotes.domain.model.LoggedInUserModel
import com.kproject.quotes.domain.model.quotes.QuoteModel
import com.kproject.quotes.domain.repository.QuotesRepository
import com.kproject.quotes.domain.usecase.auth.LogoutUseCase
import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCase
import com.kproject.quotes.domain.usecase.quotes.validation.QuoteInputValidationUseCase
import com.kproject.quotes.presentation.model.PostQuote
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.model.fromModel
import com.kproject.quotes.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.Typography.quote

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val getPreferenceUseCase: GetPreferenceUseCase,
    private val logoutUseCase: LogoutUseCase,
    val quoteInputValidationUseCase: QuoteInputValidationUseCase
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

    fun postQuote(
        postQuote: PostQuote,
        onSuccess: (quote: Quote) -> Unit,
        onError: () -> Unit,
    ) {
        viewModelScope.launch {
            val quoteModel = QuoteModel(quote = postQuote.quote, author = postQuote.author)
            val result = quotesRepository.create(quoteModel)
            result.collect { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        resultState.data?.let { postedQuoteModel ->
                            onSuccess.invoke(postedQuoteModel.fromModel())
                        } ?: onError.invoke()
                    }
                    is ResultState.Error -> {
                        onError.invoke()
                    }
                    else -> {}
                }
            }
        }
    }
}