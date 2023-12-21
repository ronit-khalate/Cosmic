package com.ronit.cosmic.storage_feature.data

import androidx.room.withTransaction
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

        articleDb.withTransaction {
            articleDb.savedArticleDao.saveArticle(article)
            articleDb.cachedArticleDao.saveArticle(article.id)
        }
    }

    override suspend fun removeArticle(article: SavedArticleEntity) {

        articleDb.withTransaction {

            articleDb.savedArticleDao.removeArticle(article)
            articleDb.cachedArticleDao.unSaveArticle(article.id)
        }

    }

    override suspend fun getSavedArticlesId(): List<Int> {

        return articleDb.savedArticleDao.getAllSavedArticlesIDs()
    }
}
