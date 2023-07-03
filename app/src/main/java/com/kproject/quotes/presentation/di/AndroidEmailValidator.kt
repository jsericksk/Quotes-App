package com.kproject.quotes.presentation.di

import androidx.core.util.PatternsCompat
import com.kproject.quotes.domain.usecase.auth.validation.EmailValidator

class AndroidEmailValidator : EmailValidator {

    override fun isValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }
}