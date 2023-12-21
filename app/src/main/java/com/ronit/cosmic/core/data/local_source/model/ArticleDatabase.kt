package com.ronit.cosmic.core.data.local_source.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ronit.cosmic.core.data.local_source.dao.CachedArticleDao
import com.ronit.cosmic.core.data.local_source.dao.RemoteKeysDao
import com.ronit.cosmic.core.data.local_source.dao.SavedArticleDao

@Database(
        entities = [CachedArticleEntity::class,RemotePageKeysEntity::class,SavedArticleEntity::class],
        version = 1
)
abstract class ArticleDatabase :RoomDatabase(){

    abstract val cachedArticleDao: CachedArticleDao
    abstract val remotePageKeysDao: RemoteKeysDao
    abstract val savedArticleDao: SavedArticleDao
}