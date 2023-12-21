package com.ronit.cosmic.core.data.mappers

import com.ronit.cosmic.core.data.local_source.model.CachedArticleEntity
import com.ronit.cosmic.core.data.local_source.model.SavedArticleEntity
import com.ronit.cosmic.core.data.remote_source.model.ArticleDto
import com.ronit.cosmic.feature_feed.domain.Article
import com.ronit.cosmic.storage_feature.domain.SavedArticle


fun ArticleDto.toArticleEntity():CachedArticleEntity{

    return CachedArticleEntity(
            id =this.id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary

    )
}

fun CachedArticleEntity.toArticle():Article{
    return Article(
            id =id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary

    )
}fun SavedArticleEntity.toSavedArticle():SavedArticle{
    return SavedArticle(
            id =id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary

    )
}