package com.kproject.quotes.domain.usecase.auth.validation

interface EmailValidator {
    fun isValidEmail(email: String): Boolean
}