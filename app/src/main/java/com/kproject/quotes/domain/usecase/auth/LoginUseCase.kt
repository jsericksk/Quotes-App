package com.kproject.quotes.domain.usecase.auth

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.Login
import kotlinx.coroutines.flow.Flow

fun interface LoginUseCase {
    suspend operator fun invoke(login: Login): Flow<ResultState<Unit>>
}