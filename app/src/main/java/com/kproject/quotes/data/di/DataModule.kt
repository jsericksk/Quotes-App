package com.kproject.quotes.data.di

import android.content.Context
import com.kproject.quotes.data.remote.auth.AuthAuthenticator
import com.kproject.quotes.data.remote.auth.AuthInterceptor
import com.kproject.quotes.data.remote.service.ApiConstants
import com.kproject.quotes.data.remote.service.AuthApiService
import com.kproject.quotes.data.remote.service.QuotesApiService
import com.kproject.quotes.data.repository.PreferenceRepositoryImpl
import com.kproject.quotes.data.repository.QuotesRepositoryImpl
import com.kproject.quotes.data.repository.auth.AuthRepositoryImpl
import com.kproject.quotes.data.repository.auth.TokenManagerRepository
import com.kproject.quotes.domain.repository.AuthRepository
import com.kproject.quotes.domain.repository.PreferenceRepository
import com.kproject.quotes.domain.repository.QuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCustomOkHttpClient(tokenManagerRepository: TokenManagerRepository): OkHttpClient {
        val authInterceptor = AuthInterceptor(tokenManagerRepository)
        val authAuthenticator = AuthAuthenticator(tokenManagerRepository)
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideQuotesApiService(customOkHttpClient: OkHttpClient): QuotesApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(customOkHttpClient)
            .build()
        return retrofit.create(QuotesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenManagerRepository(
        @ApplicationContext applicationContext: Context
    ): TokenManagerRepository {
        return TokenManagerRepository(applicationContext)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        tokenManagerRepository: TokenManagerRepository,
        authApiService: AuthApiService,
        preferenceRepository: PreferenceRepository
    ): AuthRepository {
        return AuthRepositoryImpl(tokenManagerRepository, authApiService, preferenceRepository)
    }

    @Provides
    @Singleton
    fun provideQuotesRepository(quotesApiService: QuotesApiService): QuotesRepository {
        return QuotesRepositoryImpl(quotesApiService)
    }

    @Provides
    @Singleton
    fun providePreferenceRepository(@ApplicationContext applicationContext: Context): PreferenceRepository {
        return PreferenceRepositoryImpl(applicationContext)
    }
}