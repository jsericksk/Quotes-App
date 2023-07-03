package com.kproject.quotes.domain.model.auth

data class SignUp(
    val email: String = "",
    val username: String = "",
    val password: String = ""
)