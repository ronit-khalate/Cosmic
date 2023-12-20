package com.ronit.cosmic.core.data.local_source.model.dao
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ronit.cosmic.core.data.local_source.model.ArticleEntity
import com.ronit.cosmic.core.utility.Constants.ARTICLE_TABLE

@Dao
interface ArticleDao {

    @Upsert
    suspend fun  upsertAll(articles:List<ArticleEntity>)
    @Query("SELECT * FROM $ARTICLE_TABLE ORDER BY id DESC")
    fun pagingSource():PagingSource<Int,ArticleEntity>
    @Query("DELETE FROM $ARTICLE_TABLE")
    suspend fun clearAll()
}