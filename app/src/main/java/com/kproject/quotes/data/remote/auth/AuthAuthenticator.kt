package com.kproject.quotes.data.remote.auth

import com.kproject.quotes.data.remote.model.RefreshTokenBody
import com.kproject.quotes.data.remote.model.TokensResponse
import com.kproject.quotes.data.remote.service.ApiConstants
import com.kproject.quotes.data.remote.service.AuthApiService
import com.kproject.quotes.data.repository.auth.TokenManagerRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthAuthenticator(
    private val tokenManagerRepository: TokenManagerRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentRefreshToken = tokenManagerRepository.refreshToken
        val newTokensResponse = getNewTokens(currentRefreshToken)
        if (!newTokensResponse.isSuccessful || newTokensResponse.body() == null) {
            tokenManagerRepository.refreshToken = ""
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
}