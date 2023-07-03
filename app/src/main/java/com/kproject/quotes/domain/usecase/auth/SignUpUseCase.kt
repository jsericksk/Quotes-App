package com.kproject.quotes.domain.usecase.auth

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.SignUp
import kotlinx.coroutines.flow.Flow

fun interface SignUpUseCase {
    suspend operator fun invoke(signUp: SignUp): Flow<ResultState<Unit>>
}