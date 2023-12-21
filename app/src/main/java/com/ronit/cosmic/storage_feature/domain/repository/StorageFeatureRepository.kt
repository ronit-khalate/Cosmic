package com.ronit.cosmic.storage_feature.domain.repository

import com.ronit.cosmic.core.data.local_source.model.SavedArticleEntity
import kotlinx.coroutines.flow.Flow

interface StorageFeatureRepository {

    suspend fun getAllArticles(): Flow<List<SavedArticleEntity>>
    suspend fun saveArticle(article: SavedArticleEntity)

    suspend fun removeArticle(article: SavedArticleEntity)
}