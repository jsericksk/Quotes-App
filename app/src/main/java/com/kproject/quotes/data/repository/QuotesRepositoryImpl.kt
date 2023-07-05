package com.kproject.quotes.data.repository

import androidx.paging.PagingData
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.quotes.QuoteModel
import com.kproject.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow

class QuotesRepositoryImpl(

) : QuotesRepository {

    override suspend fun getAllQuotes(filter: String?): Flow<ResultState<PagingData<QuoteModel>>> {
        TODO("")
    }

    override suspend fun getQuotesFromUserId(
        userId: Int,
        filter: String?
    ): Flow<ResultState<PagingData<QuoteModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Int): Flow<ResultState<QuoteModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun create(quoteModel: QuoteModel): Flow<ResultState<QuoteModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateById(id: Int): Flow<ResultState<QuoteModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Int): Flow<ResultState<Unit>> {
        TODO("Not yet implemented")
    }
}