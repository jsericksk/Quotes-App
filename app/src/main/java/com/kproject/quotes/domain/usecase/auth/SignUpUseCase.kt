package com.kproject.quotes.domain.usecase.auth

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.auth.SignUp

fun interface SignUpUseCase {
    suspend operator fun invoke(signUp: SignUp): ResultState<Unit>
}