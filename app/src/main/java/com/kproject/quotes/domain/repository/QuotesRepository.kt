package com.kproject.quotes.domain.repository

import androidx.paging.PagingData
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.quotes.QuoteModel
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun getAllQuotes(filter: String?): Flow<ResultState<PagingData<QuoteModel>>>

    suspend fun getQuotesFromUserId(
        userId: Int,
        filter: String?
    ): Flow<ResultState<PagingData<QuoteModel>>>

    suspend fun getById(id: Int): Flow<ResultState<QuoteModel>>

    suspend fun create(quoteModel: QuoteModel): Flow<ResultState<QuoteModel>>

    suspend fun updateById(id: Int): Flow<ResultState<QuoteModel>>

    suspend fun deleteById(id: Int): Flow<ResultState<Unit>>
}