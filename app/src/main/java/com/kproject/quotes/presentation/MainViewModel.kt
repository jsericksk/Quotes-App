package com.kproject.quotes.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPreferenceUseCase: GetPreferenceUseCase,
) : ViewModel() {
    var isUserLoggedIn by mutableStateOf(false)
        private set

    init {
        isUserLoggedIn = getPreferenceUseCase(
            key = PrefsConstants.IsUserLoggedIn,
            defaultValue = false
        )
    }
}
