package com.ronit.cosmic.core.data.remote_source.model

import com.squareup.moshi.Json

data class ArticleResponseDto(
        @Json(name = "count")
        val count :Int,
        @Json(name ="next")
        val next:String?,
        @Json(name ="previous")
        val previous:String?,

        @Json(name="results")
        val results:List<ArticleDto>
)
