package com.kproject.quotes.domain.usecase.auth

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.LoginModel
import kotlinx.coroutines.flow.Flow

fun interface LoginUseCase {
    suspend operator fun invoke(loginModel: LoginModel): Flow<ResultState<Unit>>
}