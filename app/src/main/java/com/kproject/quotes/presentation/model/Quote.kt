package com.kproject.quotes.presentation.model

import com.kproject.quotes.domain.model.quotes.QuoteModel
import com.kproject.quotes.presentation.utils.Utils
import java.util.Date

data class Quote(
    val id: Int,
    val quote: String,
    val author: String,
    val postedByUsername: String,
    val postedByUserId: Int,
    val publicationDate: Date
) {
    val formattedDate = Utils.getFormattedDate(publicationDate)
}

val fakeQuotesList = (0..20).map { index ->
    Quote(
        id = index,
        quote = "A imaginação é mais importante que o conhecimento.",
        author = "Albert Einstein",
        postedByUsername = "John",
        postedByUserId = 1,
        publicationDate = Date()
    )
}

fun QuoteModel.fromModel() =
        Quote(id, quote, author, postedByUsername, postedByUserId, publicationDate)

fun Quote.toModel() =
        QuoteModel(id, quote, author, postedByUsername, postedByUserId, publicationDate)