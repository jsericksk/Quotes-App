package com.kproject.quotes.presentation.di

import com.kproject.quotes.domain.repository.AuthRepository
import com.kproject.quotes.domain.usecase.auth.LoginUseCase
import com.kproject.quotes.domain.usecase.auth.SignUpUseCase
import com.kproject.quotes.domain.usecase.auth.validation.EmailValidator
import com.kproject.quotes.domain.usecase.auth.validation.ValidateEmailUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidateEmailUseCaseImpl
import com.kproject.quotes.domain.usecase.auth.validation.ValidatePasswordUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidatePasswordUseCaseImpl
import com.kproject.quotes.domain.usecase.auth.validation.ValidateRepeatedPasswordUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidateRepeatedPasswordUseCaseImpl
import com.kproject.quotes.domain.usecase.auth.validation.ValidateUsernameUseCase
import com.kproject.quotes.domain.usecase.auth.validation.ValidateUsernameUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDomainModule {

    @Provides
    @Singleton
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository::signUp)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository::login)
    }

    @Provides
    @Singleton
    fun provideEmailValidator(): EmailValidator {
        return AndroidEmailValidator()
    }

    @Provides
    @Singleton
    fun provideValidateUsernameUseCase(): ValidateUsernameUseCase {
        return ValidateUsernameUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(emailValidator: EmailValidator): ValidateEmailUseCase {
        return ValidateEmailUseCaseImpl(emailValidator)
    }

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideValidateRepeatedPasswordUseCase(): ValidateRepeatedPasswordUseCase {
        return ValidateRepeatedPasswordUseCaseImpl()
    }
}