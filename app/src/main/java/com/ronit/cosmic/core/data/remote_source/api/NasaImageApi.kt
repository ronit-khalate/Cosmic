package com.ronit.cosmic.core.data.remote_source.api

import com.ronit.cosmic.core.data.remote_source.model.imageDto.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaImageApi {

    @GET("search")
    suspend fun getImageInfoByKeywords(
        @Query("keywords")keyword:String,
        @Query("media_type")mediaType:String="image",
        @Query("page_size") pageSize:Int=30
    ): ImageResponse
}