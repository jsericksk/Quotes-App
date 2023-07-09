package com.kproject.quotes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.commom.exception.QuoteException
import com.kproject.quotes.data.remote.model.quotes.toBody
import com.kproject.quotes.data.remote.model.quotes.toQuoteModel
import com.kproject.quotes.data.remote.paging.QuotesApiPagingSource
import com.kproject.quotes.data.remote.service.QuotesApiService
import com.kproject.quotes.domain.model.quotes.QuoteModel
import com.kproject.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QuotesRepositoryImpl(
    private val quotesApiService: QuotesApiService
) : QuotesRepository {

    override fun getAllQuotes(filter: String?): Flow<PagingData<QuoteModel>> {
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 1),
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
        return Pager(
            config = PagingConfig(pageSize = 15, prefetchDistance = 1),
            pagingSourceFactory = {
                QuotesApiPagingSource(
                    quotesApiService = quotesApiService,
                    userId = userId,
                    filter = filter,
                )
            }
        ).flow
    }

    override suspend fun getById(id: Int): Flow<ResultState<QuoteModel>> = flow {
        try {
            emit(ResultState.Loading)
            val response = quotesApiService.getById(id)
            if (response.isSuccessful) {
                response.body()?.let { quoteResponse ->
                    val quoteModel = quoteResponse.toQuoteModel()
                    emit(ResultState.Success(quoteModel))
                } ?: emit(ResultState.Error(QuoteException.UnknownError))
            } else {
                if (response.code() == 401) {
                    emit(ResultState.Error(QuoteException.RefreshTokenExpired))
                    return@flow
                }
                if (response.code() == 404) {
                    emit(ResultState.Error(QuoteException.QuoteDoesNotExist))
                    return@flow
                }
                emit(ResultState.Error(QuoteException.UnknownError))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(QuoteException.UnknownError))
        }
    }

    override suspend fun create(quoteModel: QuoteModel): Flow<ResultState<QuoteModel>> = flow {
        try {
            emit(ResultState.Loading)
            val response = quotesApiService.create(quoteModel.toBody())
            if (response.isSuccessful) {
                response.body()?.let { quoteResponse ->
                    emit(ResultState.Success(quoteResponse.toQuoteModel()))
                } ?: emit(ResultState.Error(QuoteException.UnknownError))
                return@flow
            } else {
                if (response.code() == 401) {
                    emit(ResultState.Error(QuoteException.RefreshTokenExpired))
                    return@flow
                }
            }
            emit(ResultState.Error(QuoteException.UnknownError))
        } catch (e: Exception) {
            emit(ResultState.Error(QuoteException.UnknownError))
        }
    }

    override suspend fun updateById(quoteModel: QuoteModel): Flow<ResultState<Unit>> = flow {
        try {
            emit(ResultState.Loading)
            val response = quotesApiService.update(
                id = quoteModel.id,
                quoteBody = quoteModel.toBody()
            )
            if (response.isSuccessful) {
                emit(ResultState.Success())
                return@flow
            } else {
                if (response.code() == 401) {
                    emit(ResultState.Error(QuoteException.RefreshTokenExpired))
                    return@flow
                }
            }
            emit(ResultState.Error(QuoteException.UnknownError))
        } catch (e: Exception) {
            emit(ResultState.Error(QuoteException.UnknownError))
        }
    }

    override suspend fun deleteById(id: Int): Flow<ResultState<Unit>> = flow {
        try {
            emit(ResultState.Loading)
            val response = quotesApiService.delete(id)
            if (response.isSuccessful) {
                emit(ResultState.Success())
                return@flow
            } else {
                if (response.code() == 401) {
                    emit(ResultState.Error(QuoteException.RefreshTokenExpired))
                    return@flow
                }
            }
            emit(ResultState.Error(QuoteException.UnknownError))
        } catch (e: Exception) {
            emit(ResultState.Error(QuoteException.UnknownError))
        }
    }
}