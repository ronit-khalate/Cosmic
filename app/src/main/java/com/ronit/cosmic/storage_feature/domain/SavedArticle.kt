package com.ronit.cosmic.storage_feature.domain

data class SavedArticle(
    val id:Int,
    val title:String,
    val newsUrl:String?,
    val imageUrl:String?,
    val newsSite:String?,
    val summary:String
)
