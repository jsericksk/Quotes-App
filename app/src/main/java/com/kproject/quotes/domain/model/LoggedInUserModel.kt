package com.kproject.quotes.domain.model

data class LoggedInUserModel(
    val userId: Int = 0,
    val email: String = "",
    val username: String = ""
)