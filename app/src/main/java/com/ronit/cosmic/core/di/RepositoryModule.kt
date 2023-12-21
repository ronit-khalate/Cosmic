package com.ronit.cosmic.core.di

import com.ronit.cosmic.core.data.local_source.model.ArticleDatabase
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
}