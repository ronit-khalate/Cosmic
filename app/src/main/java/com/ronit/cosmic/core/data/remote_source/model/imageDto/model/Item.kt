package com.ronit.cosmic.core.data.remote_source.model.imageDto.model

import com.squareup.moshi.Json

data class Item(
    @field:Json(name="data")
    val data: List<Data>,
    val href: String,
    val links: List<Link>
)