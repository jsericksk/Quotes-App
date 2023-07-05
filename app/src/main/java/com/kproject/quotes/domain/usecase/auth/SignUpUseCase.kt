package com.kproject.quotes.domain.usecase.auth

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.SignUpModel
import kotlinx.coroutines.flow.Flow

fun interface SignUpUseCase {
    suspend operator fun invoke(signUpModel: SignUpModel): Flow<ResultState<Unit>>
}