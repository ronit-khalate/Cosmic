package com.ronit.cosmic.core.data.local_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ronit.cosmic.core.utility.Constants.SAVED_ARTICLE_TABLE

@Entity(tableName =SAVED_ARTICLE_TABLE)
data class SavedArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val title:String,
    val newsUrl:String?,
    val imageUrl:String?,
    val newsSite:String?,
    val summary:String
)
