package com.kproject.quotes.domain.model.auth

data class SignUpModel(
    val email: String = "",
    val username: String = "",
    val password: String = ""
)