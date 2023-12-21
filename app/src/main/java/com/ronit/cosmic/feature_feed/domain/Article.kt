package com.ronit.cosmic.feature_feed.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Article(
        val id:Int,
        val title:String,
        val newsUrl:String?,
        val imageUrl:String?,
        val newsSite:String?,
        val summary:String,
        var isSaved:Boolean=false
)
