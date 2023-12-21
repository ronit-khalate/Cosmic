package com.ronit.cosmic.feature_feed.data

import androidx.paging.Pager
import com.ronit.cosmic.core.data.local_source.model.CachedArticleEntity
import com.ronit.cosmic.feature_feed.domain.FeedRepository
import javax.inject.Inject

class FeedPagerRepositoryImpl @Inject constructor(
        private val feedPager: Pager<Int, CachedArticleEntity>,
):FeedRepository {
    override fun getArticlePager() = feedPager.flow
}