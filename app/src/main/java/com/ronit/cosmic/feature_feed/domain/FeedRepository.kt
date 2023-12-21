package com.ronit.cosmic.feature_feed.domain

import androidx.paging.PagingData
import com.ronit.cosmic.core.data.local_source.model.CachedArticleEntity
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getArticlePager():Flow<PagingData<CachedArticleEntity>>

}