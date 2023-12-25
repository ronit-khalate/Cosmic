package com.ronit.cosmic.core.domain

import com.squareup.moshi.Json

data class Data(
    @field:Json(name ="title" )
    val title: String,
    @field:Json(name = "nasa_id")
    val nasa_id: String,
)