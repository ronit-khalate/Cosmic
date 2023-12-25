package com.ronit.cosmic.core.data.remote_source.api

import com.ronit.cosmic.core.data.remote_source.model.article_dto.ArticleResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {


    @GET(" /v4/articles/?")
    suspend fun getArticles(
        @Query("limit")limit:Int,
        @Query("offset") offset:Int
    ): ArticleResponseDto
}