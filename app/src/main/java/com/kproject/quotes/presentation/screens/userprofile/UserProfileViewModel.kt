package com.kproject.quotes.presentation.screens.userprofile

import android.util.Log
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
import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCase
import com.kproject.quotes.presentation.model.Quote
import com.kproject.quotes.presentation.model.fromModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val getPreferenceUseCase: GetPreferenceUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> get() = _uiState

    fun getQuotesFromUser(): Flow<PagingData<Quote>> {
        val loggedInUserModel = getPreferenceUseCase(
            key = PrefsConstants.LoggedInUserInfo,
            defaultValue = LoggedInUserModel().toJson()
        ).fromJson(LoggedInUserModel::class.java)

        return quotesRepository.getQuotesFromUserId(
            filter = _uiState.value.searchQuery,
            userId = loggedInUserModel.userId
        ).cachedIn(viewModelScope).map { pagingDataModel ->
            pagingDataModel.map { quoteModel ->
                quoteModel.fromModel()
            }
        }
    }
}