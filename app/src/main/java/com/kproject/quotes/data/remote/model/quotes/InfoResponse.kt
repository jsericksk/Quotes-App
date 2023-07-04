package com.kproject.quotes.data.remote.model.quotes

data class InfoResponse(
    val info: Info,
    val results: List<QuoteResponse>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val previous: String?
)