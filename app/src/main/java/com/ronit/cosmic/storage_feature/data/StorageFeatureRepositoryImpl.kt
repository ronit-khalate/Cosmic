package com.ronit.cosmic.storage_feature.data

import com.ronit.cosmic.core.data.local_source.model.ArticleDatabase
import com.ronit.cosmic.core.data.local_source.model.SavedArticleEntity
import com.ronit.cosmic.storage_feature.domain.repository.StorageFeatureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StorageFeatureRepositoryImpl @Inject constructor(
        private val articleDb:ArticleDatabase
): StorageFeatureRepository {
    override suspend fun getAllArticles(): Flow<List<SavedArticleEntity>> {
        return articleDb.savedArticleDao.getAllSavedArticles()
    }

    override suspend fun saveArticle(article: SavedArticleEntity) {
        return articleDb.savedArticleDao.saveArticle(article)
    }

    override suspend fun removeArticle(article: SavedArticleEntity) {
        return articleDb.savedArticleDao.removeArticle(article)
    }
}
