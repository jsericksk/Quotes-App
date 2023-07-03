package com.kproject.quotes.domain.repository.auth

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.Login
import com.kproject.quotes.domain.model.auth.SignUp

interface AuthRepository {

    suspend fun login(login: Login): ResultState<Unit>

    suspend fun signUp(signUp: SignUp): ResultState<Unit>
}