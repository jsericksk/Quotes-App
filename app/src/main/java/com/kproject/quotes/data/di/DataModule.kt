package com.kproject.quotes.data.di

import android.content.Context
import com.kproject.quotes.data.remote.auth.AuthAuthenticator
import com.kproject.quotes.data.remote.auth.AuthInterceptor
import com.kproject.quotes.data.remote.service.ApiConstants
import com.kproject.quotes.data.remote.service.AuthApiService
import com.kproject.quotes.data.repository.auth.TokenManagerRepository
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
    fun provideTokenManagerRepository(
        @ApplicationContext applicationContext: Context
    ): TokenManagerRepository {
        return TokenManagerRepository(applicationContext)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenManagerRepository: TokenManagerRepository): OkHttpClient {
        val authInterceptor = AuthInterceptor(tokenManagerRepository)
        val authAuthenticator = AuthAuthenticator(tokenManagerRepository)
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstants.BaseUrl)
            .build()
        return retrofit.create(AuthApiService::class.java)
    }
}