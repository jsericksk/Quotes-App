package com.kproject.quotes.domain.repository

import com.kproject.quotes.commom.ResultState
import com.kproject.quotes.domain.model.quotes.QuoteModel
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun getById(id: Int): Flow<ResultState<QuoteModel>>
}