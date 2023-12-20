package com.ronit.cosmic.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ronit.cosmic.core.data.remote_source.api.ArticleApi
import com.ronit.cosmic.core.utility.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{


        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleApi(retrofit: Retrofit):ArticleApi{

        return retrofit.create(ArticleApi::class.java)
    }
}