package com.ronit.cosmic.feature_feed.presentation

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebPAgeScreen(
    newsUrl:String,
    onBackPressed:()->Unit
){

    Scaffold(
            topBar = {

                Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                            modifier = Modifier
                                .size(60.dp),
                            onClick = onBackPressed
                    ){
                        Image(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),

        ) {

            AndroidView(
                    factory = {context->
                        WebView(context).apply {

                            layoutParams=ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                            )

                            webViewClient= WebViewClient()
                            loadUrl(newsUrl)
                        }
                    },
                    update = {
                        it.loadUrl(newsUrl)
                    }
            )
        }
    }

}
