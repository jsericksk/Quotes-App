package com.kproject.quotes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.quotes.domain.usecase.preference.IsRefreshTokenExpiredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isTokenExpiredUseCase: IsRefreshTokenExpiredUseCase,
) : ViewModel() {

    val isRefreshTokenExpired: StateFlow<Boolean> = isTokenExpiredUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
}