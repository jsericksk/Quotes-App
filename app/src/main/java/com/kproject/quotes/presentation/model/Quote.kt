package com.kproject.quotes.presentation.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Quote(
    val id: Int,
    val quote: String,
    val author: String,
    val postedByUsername: String,
    val postedByUserId: Int,
    val publicationDate: Date
) {
    val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(publicationDate)
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

