package com.ronit.cosmic.core.data.local_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ronit.cosmic.core.utility.Constants.ARTICLE_TABLE

@Entity(tableName = ARTICLE_TABLE)
data class ArticleEntity(
        @PrimaryKey(autoGenerate = false)
        val id:Int,
        val title:String,
        val newsUrl:String?,
        val imageUrl:String?,
        val newsSite:String?,
        val summary:String
)