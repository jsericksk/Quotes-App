package com.kproject.quotes.data.remote.auth

import com.kproject.quotes.data.repository.auth.TokenManagerRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManagerRepository: TokenManagerRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer ${tokenManagerRepository.accessToken}")
        return chain.proceed(request.build())
    }
}