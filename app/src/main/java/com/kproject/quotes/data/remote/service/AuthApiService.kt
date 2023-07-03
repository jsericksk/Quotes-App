package com.kproject.quotes.data.remote.service

import com.kproject.quotes.data.remote.model.RefreshTokenBody
import com.kproject.quotes.data.remote.model.TokensResponse
import com.kproject.quotes.domain.model.auth.Login
import com.kproject.quotes.domain.model.auth.SignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/register")
    suspend fun signUp(@Body signUp: SignUp): Response<Int>

    @POST("auth/login")
    suspend fun login(@Body login: Login): Response<TokensResponse>

    @POST("auth/refresh-token")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenBody): Response<TokensResponse>
}