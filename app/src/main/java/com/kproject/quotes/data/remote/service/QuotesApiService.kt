package com.kproject.quotes.data.remote.service

import com.kproject.quotes.data.remote.model.quotes.InfoResponse
import com.kproject.quotes.data.remote.model.quotes.QuoteBody
import com.kproject.quotes.data.remote.model.quotes.QuoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface QuotesApiService {

    @GET("quotes")
    suspend fun getQuotes(
        @Query("page") page: Int?,
        @Query("filter") filter: String? = null
    ): Response<InfoResponse>

    @GET("quotes/{id}")
    suspend fun getById(@Path("id") id: Int): Response<QuoteResponse>

    @POST("quotes")
    suspend fun create(@Body quoteBody: QuoteBody): Response<QuoteResponse>

    @PUT("quotes/{id}")
    suspend fun update(@Path("id") id: Int, @Body quoteBody: QuoteBody): Response<Unit>

    @DELETE("quotes/{id}")
    suspend fun delete(@Path("id") id: Int): Response<Unit>
}