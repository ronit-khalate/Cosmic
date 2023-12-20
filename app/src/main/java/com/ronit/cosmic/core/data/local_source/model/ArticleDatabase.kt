package com.ronit.cosmic.core.data.local_source.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ronit.cosmic.core.data.local_source.model.dao.ArticleDao
import com.ronit.cosmic.core.data.local_source.model.dao.RemoteKeysDao

@Database(
        entities = [ArticleEntity::class,RemotePageKeys::class],
        version = 1
)
abstract class ArticleDatabase :RoomDatabase(){

    abstract val articleDao:ArticleDao
    abstract val remotePageKeysDao:RemoteKeysDao
}