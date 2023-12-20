package com.ronit.cosmic.feature_feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ronit.cosmic.core.data.mappers.toArticle
import com.ronit.cosmic.feature_feed.data.FeedPagerRepositoryImpl
import com.ronit.cosmic.feature_feed.domain.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
        private val feedPagerRepositoryImpl: FeedPagerRepositoryImpl
):ViewModel() {

    val articlePagingFlow = feedPagerRepositoryImpl.getArticlePager()
        .map {pagingData->
            pagingData.map { it.toArticle() }
        }
        .cachedIn(viewModelScope)
}