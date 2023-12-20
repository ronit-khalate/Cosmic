package com.ronit.cosmic.core.di

import android.content.Context
import androidx.room.Room
import com.ronit.cosmic.core.data.local_source.model.ArticleDatabase
import com.ronit.cosmic.core.utility.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context):ArticleDatabase{

        return Room.databaseBuilder(
                context=context,
                ArticleDatabase::class.java,
                DATABASE_NAME
        ).build()
    }

}