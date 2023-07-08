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
import com.kproject.quotes.domain.model.quotes.QuoteModel
import com.kproject.quotes.domain.repository.QuotesRepository
import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCase
import com.kproject.quotes.domain.usecase.quotes.validation.QuoteInputValidationUseCase
import com.kproject.quotes.presentation.model.PostQuote
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.model.fromModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val getPreferenceUseCase: GetPreferenceUseCase,
    val quoteInputValidationUseCase: QuoteInputValidationUseCase
) : ViewModel() {
    var quotes: Flow<PagingData<Quote>>

    init {
        val loggedInUserModel = getPreferenceUseCase(
            key = PrefsConstants.LoggedInUserInfo,
            defaultValue = LoggedInUserModel().toJson()
        ).fromJson(LoggedInUserModel::class.java)
        quotes = quotesRepository.getQuotesFromUserId(
            filter = "",
            userId = loggedInUserModel.userId
        ).map { pagingDataModel ->
            pagingDataModel.map { quoteModel -> quoteModel.fromModel() }
        }.cachedIn(viewModelScope)
    }

    fun editQuote(
        quoteId: Int,
        postQuote: PostQuote,
        onSuccess: () -> Unit,
        onError: () -> Unit,
    ) {
        viewModelScope.launch {
            val quoteModel = QuoteModel(
                id = quoteId,
                quote = postQuote.quote,
                author = postQuote.author
            )
            val result = quotesRepository.updateById(quoteModel)
            result.collect { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        onSuccess.invoke()
                    }
                    is ResultState.Error -> {
                        onError.invoke()
                    }
                    else -> {}
                }
            }
        }
    }

    fun deleteQuote(
        quote: Quote,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            val result = quotesRepository.deleteById(quote.id)
            result.collect { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        onSuccess.invoke()
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