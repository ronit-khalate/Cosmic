package com.ronit.cosmic.core.data.local_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ronit.cosmic.core.data.local_source.model.SavedArticleEntity
import com.ronit.cosmic.core.utility.Constants.SAVED_ARTICLE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedArticleDao {

    @Insert()
    suspend fun saveArticle(article:SavedArticleEntity)

    @Delete
    suspend fun removeArticle(article: SavedArticleEntity)

    @Query("SELECT * FROM $SAVED_ARTICLE_TABLE")
    fun getAllSavedArticles():Flow<List<SavedArticleEntity>>

    @Query("SELECT id FROM $SAVED_ARTICLE_TABLE")
    suspend fun getAllSavedArticlesIDs():List<Int>
}