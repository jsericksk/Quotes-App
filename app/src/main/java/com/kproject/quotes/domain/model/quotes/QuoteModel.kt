package com.kproject.quotes.domain.model.quotes

import java.util.Date

data class QuoteModel(
    val id: Int = 0,
    val quote: String = "",
    val author: String = "",
    val postedByUsername: String = "",
    val postedByUserId: Int = 0,
    val publicationDate: Date = Date()
)