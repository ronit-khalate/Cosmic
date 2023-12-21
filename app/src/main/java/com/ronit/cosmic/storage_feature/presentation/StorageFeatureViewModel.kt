package com.ronit.cosmic.storage_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.cosmic.core.data.mappers.toSavedArticle
import com.ronit.cosmic.storage_feature.data.StorageFeatureRepositoryImpl
import com.ronit.cosmic.storage_feature.domain.SavedArticle
import com.ronit.cosmic.storage_feature.domain.repository.StorageFeatureRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageFeatureViewModel @Inject constructor(
        private val repository: StorageFeatureRepository
):ViewModel() {

        private var _savedArticleListFlow = MutableStateFlow<List<SavedArticle>>(emptyList())
        val savedArticleListFlow:StateFlow<List<SavedArticle>> = _savedArticleListFlow.asStateFlow()

        init {
                viewModelScope.launch {

                        repository.getAllArticles()
                                .collect{savedArticleEntityList->
                                        _savedArticleListFlow.value = savedArticleEntityList.map {savedArticleEntity->
                                                savedArticleEntity.toSavedArticle()
                                        }
                                }
                }
        }



}