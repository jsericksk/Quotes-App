package com.kproject.quotes.data.repository.auth

import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.commom.exception.AuthException
import com.kproject.quotes.data.remote.model.ErrorResponse
import com.kproject.quotes.data.remote.service.AuthApiService
import com.kproject.quotes.data.toJson
import com.kproject.quotes.domain.model.LoggedInUserModel
import com.kproject.quotes.domain.model.auth.LoginModel
import com.kproject.quotes.domain.model.auth.SignUpModel
import com.kproject.quotes.domain.repository.AuthRepository
import com.kproject.quotes.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val EmailNotAvailableCode = "email_not_available"
const val UsernameNotAvailableCode = "username_not_available"

class AuthRepositoryImpl(
    private val tokenManagerRepository: TokenManagerRepository,
    private val authApiService: AuthApiService,
    private val preferenceRepository: PreferenceRepository
) : AuthRepository {

    override suspend fun signUp(signUpModel: SignUpModel): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading)
        try {
            val response = authApiService.signUp(signUpModel)
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

    override suspend fun login(loginModel: LoginModel): Flow<ResultState<Unit>> = flow {
        emit(ResultState.Loading)
        try {
            val response = authApiService.login(loginModel)
            if (response.isSuccessful) {
                response.body()?.let { tokensResponse ->
                    tokenManagerRepository.accessToken = tokensResponse.accessToken
                    tokenManagerRepository.refreshToken = tokensResponse.refreshToken
                    saveUserInfo(tokensResponse.accessToken)
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

    private fun saveUserInfo(accessToken: String) {
        try {
            val jwt = JWT(accessToken)
            val userId = jwt.getClaim("uid").asInt()!!
            val email = jwt.getClaim("email").asString()!!
            val username = jwt.getClaim("username").asString()!!
            val loggedInUserModel = LoggedInUserModel(
                userId = userId,
                email = email,
                username = username
            )
            preferenceRepository.savePreference(
                key = PrefsConstants.IsUserLoggedIn,
                value = true
            )
            preferenceRepository.savePreference(
                key = PrefsConstants.LoggedInUserInfo,
                value = loggedInUserModel.toJson()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}