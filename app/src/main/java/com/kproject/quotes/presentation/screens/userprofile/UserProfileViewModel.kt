package com.kproject.quotes.presentation.screens.userprofile

import androidx.lifecycle.ViewModel
import com.kproject.quotes.domain.repository.QuotesRepository
import com.kproject.quotes.presentation.screens.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState


}