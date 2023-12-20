package com.ronit.cosmic.core.data.mappers

import com.ronit.cosmic.core.data.local_source.model.ArticleEntity
import com.ronit.cosmic.core.data.remote_source.model.ArticleDto
import com.ronit.cosmic.feature_feed.domain.Article


fun ArticleDto.toArticleEntity():ArticleEntity{

    return ArticleEntity(
            id =this.id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary

    )
}

fun ArticleEntity.toArticle():Article{
    return Article(
            id =id,
            title=title,
            newsUrl=newsUrl,
            imageUrl=imageUrl,
            newsSite=newsSite,
            summary=summary

    )
}