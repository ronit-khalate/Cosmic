package com.ronit.cosmic.core.data.local_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ronit.cosmic.core.utility.Constants.CACHED_ARTICLE_TABLE

@Entity(tableName = CACHED_ARTICLE_TABLE)
data class CachedArticleEntity(
        @PrimaryKey(autoGenerate = false)
        val id:Int,
        val title:String,
        val newsUrl:String?,
        val imageUrl:String?,
        val newsSite:String?,
        val summary:String,
        var isSaved:Boolean=false
)