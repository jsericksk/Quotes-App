package com.kproject.quotes.presentation.di

import com.kproject.quotes.domain.usecase.quotes.validation.QuoteInputValidationUseCase
import com.kproject.quotes.domain.usecase.quotes.validation.QuoteInputValidationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuotesDomainModule {

    @Provides
    @Singleton
    fun provideQuoteInputValidationUseCase(): QuoteInputValidationUseCase {
        return QuoteInputValidationUseCaseImpl()
    }
}