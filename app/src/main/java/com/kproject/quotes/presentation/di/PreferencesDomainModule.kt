package com.kproject.quotes.presentation.di

import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCase
import com.kproject.quotes.domain.usecase.preference.GetPreferenceUseCaseImpl
import com.kproject.quotes.domain.usecase.preference.SavePreferenceUseCase
import com.kproject.quotes.domain.usecase.preference.SavePreferenceUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesDomainModule {

    @Binds
    abstract fun bindGetPreferenceUseCase(
        getPreferenceUseCaseImpl: GetPreferenceUseCaseImpl
    ): GetPreferenceUseCase

    @Binds
    abstract fun bindSavePreferenceUseCase(
        savePreferenceUseCaseImpl: SavePreferenceUseCaseImpl
    ): SavePreferenceUseCase
}