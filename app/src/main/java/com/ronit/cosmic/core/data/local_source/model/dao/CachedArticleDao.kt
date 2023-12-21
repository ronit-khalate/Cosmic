package com.ronit.cosmic.core.data.local_source.model.dao
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ronit.cosmic.core.data.local_source.model.CachedArticleEntity
import com.ronit.cosmic.core.utility.Constants.CACHED_ARTICLE_TABLE

@Dao
interface CachedArticleDao {

    @Upsert
    suspend fun  upsertAll(articles:List<CachedArticleEntity>)
    @Query("SELECT * FROM $CACHED_ARTICLE_TABLE ORDER BY id DESC")
    fun pagingSource():PagingSource<Int,CachedArticleEntity>
    @Query("DELETE FROM $CACHED_ARTICLE_TABLE")
    suspend fun clearAll()
}