package com.ronit.cosmic.core.data.remote_source.model.imageDto.model

data class Collection(
    val href: String,
    val items: List<Item>,
    val links: List<LinkX>,
    val metadata: Metadata,
    val version: String
)