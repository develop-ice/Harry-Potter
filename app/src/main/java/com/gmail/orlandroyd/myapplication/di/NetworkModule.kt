package com.gmail.orlandroyd.myapplication.di

import com.gmail.orlandroyd.myapplication.networking.ApiConstants
import com.gmail.orlandroyd.myapplication.networking.CharacterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): CharacterApi {
        return builder
            .build()
            .create(CharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttp.build())
            .addConverterFactory(MoshiConverterFactory.create())
    }

}