package com.kproject.quotes.data.remote.auth

import com.kproject.quotes.commom.constants.PrefsConstants
import com.kproject.quotes.data.remote.model.auth.RefreshTokenBody
import com.kproject.quotes.data.remote.model.auth.TokensResponse
import com.kproject.quotes.data.remote.service.ApiConstants
import com.kproject.quotes.data.remote.service.AuthApiService
import com.kproject.quotes.data.repository.auth.TokenManagerRepository
import com.kproject.quotes.data.toJson
import com.kproject.quotes.domain.model.LoggedInUserModel
import com.kproject.quotes.domain.repository.PreferenceRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthAuthenticator(
    private val tokenManagerRepository: TokenManagerRepository,
    private val preferenceRepository: PreferenceRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentRefreshToken = tokenManagerRepository.refreshToken
        val newTokensResponse = getNewTokens(currentRefreshToken)
        if (!newTokensResponse.isSuccessful || newTokensResponse.body() == null) {
            tokenManagerRepository.refreshToken = ""
            clearUserInfo()
            return null
        }

        return newTokensResponse.body()?.let { updatedTokens ->
            tokenManagerRepository.accessToken = updatedTokens.accessToken
            tokenManagerRepository.refreshToken = updatedTokens.refreshToken
            response.request().newBuilder()
                .header("Authorization", "Bearer ${updatedTokens.accessToken}")
                .build()
        }
    }

    private fun getNewTokens(refreshToken: String): retrofit2.Response<TokensResponse> {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(AuthApiService::class.java)
        val tokenCall = apiService.refreshToken(RefreshTokenBody(refreshToken))
        return tokenCall.execute()
    }

    private fun clearUserInfo() {
        preferenceRepository.savePreference(PrefsConstants.RefreshTokenExpired, true)
        preferenceRepository.savePreference(PrefsConstants.IsUserLoggedIn, false)
        preferenceRepository.savePreference(
            PrefsConstants.LoggedInUserInfo,
            LoggedInUserModel().toJson()
        )
    }
}