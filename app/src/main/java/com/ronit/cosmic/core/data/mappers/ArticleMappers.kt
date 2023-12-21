package com.ronit.cosmic.core.data.mappers

import androidx.compose.runtime.mutableStateOf
import com.ronit.cosmic.core.data.local_source.model.CachedArticleEntity
import com.ronit.cosmic.core.data.local_source.model.SavedArticleEntity
import com.ronit.cosmic.core.data.remote_source.model.ArticleDto
import com.ronit.cosmic.feature_feed.domain.Article
import com.ronit.cosmic.storage_feature.domain.SavedArticle


fun ArticleDto.toArticleEntity(isSaved:Boolean=false):CachedArticleEntity{

    return CachedArticleEntity(
            id =this.id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary,
            isSaved=isSaved

    )
}

fun CachedArticleEntity.toArticle():Article{
    return Article(
            id =id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary,
            isSaved=isSaved

    )
}

fun SavedArticleEntity.toSavedArticle():SavedArticle{
    return SavedArticle(
            id =id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary

    )
}

fun Article.toSavedArticleEntity():SavedArticleEntity{
    return SavedArticleEntity(
            id =id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary,
    )
}