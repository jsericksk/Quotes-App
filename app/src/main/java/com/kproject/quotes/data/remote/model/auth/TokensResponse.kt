package com.kproject.quotes.data.remote.model.auth

data class TokensResponse(
    val accessToken: String,
    val refreshToken: String,
)