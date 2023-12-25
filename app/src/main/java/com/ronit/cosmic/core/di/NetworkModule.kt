package com.ronit.cosmic.core.di

import com.ronit.cosmic.core.data.remote_source.api.ArticleApi
import com.ronit.cosmic.core.data.remote_source.api.NasaImageApi
import com.ronit.cosmic.core.utility.Constants.ARTICLE_BASE_URL
import com.ronit.cosmic.core.utility.Constants.NASA_IMAGES_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    @ArticleRetrofit
    fun provideArticleRetrofit():Retrofit{


        return  Retrofit.Builder()
            .baseUrl(ARTICLE_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    @NasaImageRetrofit
    fun provideNasaRetrofit():Retrofit{

        return Retrofit.Builder()
            .baseUrl(NASA_IMAGES_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideArticleApi(@ArticleRetrofit retrofit: Retrofit):ArticleApi{

        return retrofit.create(ArticleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNasaImageApi(@NasaImageRetrofit retrofit: Retrofit):NasaImageApi{

        return  retrofit.create(NasaImageApi::class.java)
    }
}



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ArticleRetrofit
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NasaImageRetrofit