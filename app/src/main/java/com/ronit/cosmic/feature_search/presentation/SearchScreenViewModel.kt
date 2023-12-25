package com.ronit.cosmic.feature_search.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.cosmic.feature_search.domain.model.ImageInfo
import com.ronit.cosmic.feature_search.domain.repository.SearchScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
        private val searchScreenRepositoryImpl: SearchScreenRepository
):ViewModel() {


    var searchTypedValue by mutableStateOf("")
        private set

    var currentPageNumber by mutableStateOf(1)
        private set


    var images by mutableStateOf<List<ImageInfo>>(emptyList())
        private set


    fun incrementPageNumber()=currentPageNumber+1
    fun onSearchKeywordEntered(keywords:String){
        searchTypedValue=keywords
    }
    fun getImageInfoByKeyword(keyWords:String){
        // Replace whitespaces with commas (,) API Requirements
        val kw= keyWords.replace("\\s+".toRegex(),",")

        viewModelScope.launch{

            val info =searchScreenRepositoryImpl.searchImageInfoByKeyWords(kw)
            images=info

        }
    }

    fun clearImageList(){
        images= emptyList()
    }
}