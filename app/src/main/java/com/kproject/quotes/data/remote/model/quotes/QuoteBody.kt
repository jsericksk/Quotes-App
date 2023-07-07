package com.kproject.quotes.data.remote.model.quotes

import com.kproject.quotes.domain.model.quotes.QuoteModel

data class QuoteBody(
    val quote: String,
    val author: String
)

fun QuoteModel.toBody() = QuoteBody(quote = quote, author = author)