package com.kproject.quotes.data.remote.model.quotes

import com.kproject.quotes.domain.model.quotes.QuoteModel

data class QuoteResponse(
    val id: Int,
    val quote: String,
    val author: String,
    val postedByUsername: String,
    val postedByUserId: Int,
    val publicationDate: String
)

fun QuoteResponse.toQuoteModel() = QuoteModel(id, quote, author, postedByUsername, postedByUserId)