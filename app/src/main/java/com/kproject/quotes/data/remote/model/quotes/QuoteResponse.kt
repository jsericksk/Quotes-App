package com.kproject.quotes.data.remote.model.quotes

data class QuoteResponse(
    val id: Int,
    val quote: String,
    val author: String,
    val postedByUsername: String,
    val postedByUserId: Int,
    val publicationDate: String
)