package com.kproject.quotes.domain.repository

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.LoginModel
import com.kproject.quotes.domain.model.auth.SignUpModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUp(signUpModel: SignUpModel): Flow<ResultState<Unit>>

    suspend fun login(loginModel: LoginModel): Flow<ResultState<Unit>>
}