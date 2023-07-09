package com.kproject.quotes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.quotes.domain.usecase.preference.IsRefreshTokenExpiredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isRefreshTokenExpiredUseCase: IsRefreshTokenExpiredUseCase,
) : ViewModel() {
    private val _isRefreshTokenExpired = MutableStateFlow(false)
    val isRefreshTokenExpired: StateFlow<Boolean> = _isRefreshTokenExpired

    init {
        viewModelScope.launch {
            isRefreshTokenExpiredUseCase().collectLatest { isExpired ->
                _isRefreshTokenExpired.value = isExpired
            }
        }
    }
}