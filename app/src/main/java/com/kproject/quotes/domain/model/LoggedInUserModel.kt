package com.kproject.quotes.domain.model

data class LoggedInUserModel(
    val userId: Int,
    val email: String,
    val username: String
)