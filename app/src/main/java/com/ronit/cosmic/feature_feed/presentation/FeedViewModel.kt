package com.ronit.cosmic.feature_feed.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ronit.cosmic.core.data.mappers.toArticle
import com.ronit.cosmic.core.data.mappers.toSavedArticle
import com.ronit.cosmic.core.data.mappers.toSavedArticleEntity
import com.ronit.cosmic.feature_feed.data.FeedPagerRepositoryImpl
import com.ronit.cosmic.feature_feed.domain.Article
import com.ronit.cosmic.feature_feed.domain.FeedRepository
import com.ronit.cosmic.storage_feature.domain.repository.StorageFeatureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
        private val feedPagerRepositoryImpl: FeedRepository,
        private val storageFeatureRepository: StorageFeatureRepository

):ViewModel() {


    val articlePagingFlow = feedPagerRepositoryImpl.getArticlePager()
        .map {pagingData->
            pagingData.map {
                it.toArticle()
            }
        }
        .cachedIn(viewModelScope)



    fun saveArticle(article:Article,onSuccess:()->Unit){

        viewModelScope.launch {

            try {

                storageFeatureRepository.saveArticle(article.toSavedArticleEntity())
                onSuccess()
            }catch (e:Exception){
                Log.d("saveArticleError",e.message.toString())
            }
        }
    }

    fun removeArticle(article: Article,onSuccess:()->Unit){

        viewModelScope.launch {

            try {

                storageFeatureRepository.removeArticle(article.toSavedArticleEntity())

                onSuccess()

            }catch (e:Exception){
                Log.d("removeArticleError",e.message.toString())
            }

        }
    }
}