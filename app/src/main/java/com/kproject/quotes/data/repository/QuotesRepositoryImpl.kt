package com.kproject.quotes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.data.remote.QuotesApiPagingSource
import com.kproject.quotes.data.remote.service.QuotesApiService
import com.kproject.quotes.domain.model.quotes.QuoteModel
import com.kproject.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow

class QuotesRepositoryImpl(
    private val quotesApiService: QuotesApiService
) : QuotesRepository {

    override fun getAllQuotes(filter: String?): Flow<PagingData<QuoteModel>> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = {
                QuotesApiPagingSource(
                    quotesApiService = quotesApiService,
                    filter = filter,
                    userId = null
                )
            }
        ).flow
    }

    override fun getQuotesFromUserId(
        userId: Int,
        filter: String?
    ): Flow<PagingData<QuoteModel>> {
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