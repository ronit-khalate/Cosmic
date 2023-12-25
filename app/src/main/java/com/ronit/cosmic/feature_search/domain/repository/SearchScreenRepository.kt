package com.ronit.cosmic.feature_search.domain.repository

import com.ronit.cosmic.feature_search.domain.model.ImageInfo

interface SearchScreenRepository {

    suspend fun searchImageInfoByKeyWords(keyword:String):List<ImageInfo>
}