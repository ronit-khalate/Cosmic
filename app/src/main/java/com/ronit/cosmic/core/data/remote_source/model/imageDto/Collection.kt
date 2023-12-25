package com.ronit.cosmic.core.domain

import com.squareup.moshi.Json

data class Collection(
    @field:Json(name = "items")
    val items: List<Item>,

)