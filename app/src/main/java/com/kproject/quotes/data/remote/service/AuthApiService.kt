package com.kproject.quotes.data.remote.service

import com.kproject.quotes.data.remote.model.auth.RefreshTokenBody
import com.kproject.quotes.data.remote.model.auth.TokensResponse
import com.kproject.quotes.domain.model.auth.LoginModel
import com.kproject.quotes.domain.model.auth.SignUpModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/register")
    suspend fun signUp(@Body signUpModel: SignUpModel): Response<Int>

    @POST("auth/login")
    suspend fun login(@Body loginModel: LoginModel): Response<TokensResponse>

    @POST("auth/refresh-token")
    fun refreshToken(@Body refreshToken: RefreshTokenBody): Call<TokensResponse>
}