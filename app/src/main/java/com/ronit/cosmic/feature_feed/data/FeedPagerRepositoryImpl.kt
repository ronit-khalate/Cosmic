package com.ronit.cosmic.feature_feed.data

import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.ronit.cosmic.core.data.local_source.model.ArticleDatabase
import com.ronit.cosmic.core.data.local_source.model.ArticleEntity
import com.ronit.cosmic.core.data.mappers.toArticle
import com.ronit.cosmic.core.data.remote_source.api.ArticleApi
import com.ronit.cosmic.feature_feed.domain.FeedRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FeedPagerRepositoryImpl @Inject constructor(
        private val feedPager: Pager<Int, ArticleEntity>,
):FeedRepository {
    override fun getArticlePager() = feedPager.flow
}