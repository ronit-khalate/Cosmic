package com.ronit.cosmic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ronit.cosmic.feature_feed.presentation.FeedViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun Home(
    userID:String?,
    feedViewModel: FeedViewModel= hiltViewModel(),
    logout:()->Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { logout() }
    ) {

    }
}