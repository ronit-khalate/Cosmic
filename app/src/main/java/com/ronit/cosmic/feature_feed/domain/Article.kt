package com.ronit.cosmic.feature_feed.domain

data class Article(
        val id:Int,
        val title:String,
        val newsUrl:String?,
        val imageUrl:String?,
        val newsSite:String?,
        val summary:String
)
