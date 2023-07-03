package com.kproject.quotes.data.repository.auth

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.commom.exception.AuthException
import com.kproject.quotes.data.remote.model.ErrorResponse
import com.kproject.quotes.data.remote.service.AuthApiService
import com.kproject.quotes.domain.model.auth.Login
import com.kproject.quotes.domain.model.auth.SignUp
import com.kproject.quotes.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val EmailNotAvailableCode = "email_not_available"
const val UsernameNotAvailableCode = "username_not_available"

class AuthRepositoryImpl(
    private val tokenManagerRepository: TokenManagerRepository,
    private val authApiService: AuthApiService,
) : AuthRepository {

    override suspend fun signUp(signUp: SignUp): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading)
        try {
            val response = authApiService.signUp(signUp)
            if (response.isSuccessful) {
                emit(ResultState.Success())
            } else {
                response.errorBody()?.let { errorBody ->
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse? =
                            Gson().fromJson(errorBody.charStream(), type)
                    errorResponse?.let { error ->
                        when (error.errorCode) {
                            EmailNotAvailableCode -> {
                                emit(ResultState.Error(AuthException.EmailNotAvailableException))
                            }
                            UsernameNotAvailableCode -> {
                                emit(ResultState.Error(AuthException.UsernameNotAvailableException))
                            }
                            else -> {
                                emit(ResultState.Error(AuthException.UnknownSignUpException))
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error(AuthException.UnknownSignUpException))
        }
    }

    override suspend fun login(login: Login): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading)
        try {
            val response = authApiService.login(login)
            if (response.isSuccessful) {
                response.body()?.let { tokensResponse ->
                    tokenManagerRepository.accessToken = tokensResponse.accessToken
                    tokenManagerRepository.refreshToken = tokensResponse.refreshToken
                    emit(ResultState.Success())
                } ?: emit(ResultState.Error(AuthException.UnknownLoginException))
            } else {
                if (response.code() == 401) {
                    emit(ResultState.Error(AuthException.WrongEmailOrPasswordException))
                    return@flow
                }
                emit(ResultState.Error(AuthException.UnknownLoginException))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(AuthException.UnknownLoginException))
        }
    }
}