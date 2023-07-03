package com.kproject.quotes.data.remote.service

import com.kproject.quotes.data.remote.model.quotes.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QuotesApiService {

    @GET("quotes/{id}")
    suspend fun getById(@Path("id") id: Int): Response<QuoteResponse>
}