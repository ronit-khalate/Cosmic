package com.ronit.cosmic.core.di

import androidx.paging.Pager
import com.ronit.cosmic.core.data.local_source.model.ArticleDatabase
import com.ronit.cosmic.core.data.local_source.model.CachedArticleEntity
import com.ronit.cosmic.feature_feed.data.FeedPagerRepositoryImpl
import com.ronit.cosmic.feature_feed.domain.FeedRepository
import com.ronit.cosmic.storage_feature.data.StorageFeatureRepositoryImpl
import com.ronit.cosmic.storage_feature.domain.repository.StorageFeatureRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideStorageFeatureRepository(articleDatabase: ArticleDatabase):StorageFeatureRepository{
        return StorageFeatureRepositoryImpl(articleDatabase)
    }

    @Provides
    fun provideFeedFeatureRepository(pager: Pager<Int,CachedArticleEntity>):FeedRepository{
        return FeedPagerRepositoryImpl(pager)
    }
}