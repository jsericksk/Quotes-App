package com.kproject.quotes.domain.repository

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.Login
import com.kproject.quotes.domain.model.auth.SignUp
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(signUp: SignUp): Flow<ResultState<Unit>>

    suspend fun login(login: Login): Flow<ResultState<Unit>>
}