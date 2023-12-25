package com.ronit.cosmic.core.data.remote_source.model.article_dto

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



data class ArticleDto(
    val id: Int,
    @field:Json(name = "image_url")
    val imageUrl: String?,
    @field:Json(name = "news_site")
    val newsSite: String?,
    val summary: String,
    val title: String,
    @field:Json(name = "url")
    val newsUrl: String?
)