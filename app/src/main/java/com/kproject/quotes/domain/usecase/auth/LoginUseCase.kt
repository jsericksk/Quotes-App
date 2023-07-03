package com.kproject.quotes.domain.usecase.auth

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.Login

fun interface LoginUseCase {
    suspend operator fun invoke(login: Login): ResultState<Unit>
}