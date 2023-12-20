package com.ronit.cosmic.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ronit.cosmic.core.data.local_source.model.ArticleDatabase
import com.ronit.cosmic.core.data.local_source.model.ArticleEntity
import com.ronit.cosmic.core.data.remote_source.ArticleRemoteMediator
import com.ronit.cosmic.core.data.remote_source.api.ArticleApi
import com.ronit.cosmic.core.utility.Constants.ARTICLES_PER_PAGE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideFeedPager(
        articleDatabase:ArticleDatabase,
        articleApi: ArticleApi
    ):Pager<Int,ArticleEntity>{

        return Pager(
                config = PagingConfig(ARTICLES_PER_PAGE),
                remoteMediator = ArticleRemoteMediator(
                        articleDb = articleDatabase,
                        articleApi=articleApi
                ),
                pagingSourceFactory = {
                    articleDatabase.articleDao.pagingSource()

                }

        )

    }
}