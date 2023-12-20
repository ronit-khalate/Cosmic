package com.ronit.cosmic.feature_feed.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Transition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ronit.cosmic.feature_feed.domain.Article
import java.nio.file.WatchEvent


@Composable
fun FeedScreen(
    viewModel: FeedViewModel= hiltViewModel(),
    articles: LazyPagingItems<Article> = viewModel.articlePagingFlow.collectAsLazyPagingItems()
){

//    val articles viewModel.articlePagingFlow.collectAsLazyPagingItems()
    LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ){

        items(
                count = articles.itemCount,
                key = articles.itemKey { it.id },
                contentType = articles.itemContentType { "contentType" }
        ){index->

            val item =articles[index]
            if(index+5==articles.itemCount){
            }
            item?.let {

                ArticleCard(article = it)
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(
    article:Article
){

    var expanded by remember{ mutableStateOf(false) }
    val density= LocalDensity.current

    Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(160.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(),
            onClick = {expanded=!expanded}
    ) {
        Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)

        ) {

           Surface(
                   modifier = Modifier
                       .fillMaxSize()
                       .heightIn(min=160.dp),
                   shape = RoundedCornerShape(20.dp)
           ) {

            AsyncImage(
                    model =ImageRequest.Builder(LocalContext.current)
                        .data(article.imageUrl)
                        .crossfade(true)
                        .crossfade(200)
                        .build(),
                    contentDescription =article.title,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,

                    )
           }


            Spacer(modifier = Modifier.height(10.dp))
            Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Green),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Start
            ) {
                Text(
                        text = article.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                )

            }
            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically{
                        with(density){ -40.dp.roundToPx()}
                    },
                    exit = shrinkVertically(),

            ) {

                Column {
                    Text(
                            text = article.summary
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                            text = "News Site : ${article.newsSite}",
                            modifier = Modifier
                                .clickable {  }
                    )
                }



            }




        }




    }
}

@Preview(showBackground = true)
@Composable
fun preview(){

    ArticleCard(
        article =Article(
                id = 21892,
                title = "D-Orbit ION Space Tug Hosts In-Orbit Refueling Demo",
                newsUrl = "",
                imageUrl = "",
                newsSite = "This is News Site",
                summary = "A payload launched aboard the thirteenth D-Orbit ION space tug will demonstrate key technology that will enable future in-orbit refueling systems. The thirteenth D-Orbit ION space tug, which was named Daring Diego, was launched aboard a SpaceX Falcon 9 rideshare mission on 1 December, carrying eight passengers and four hosted payloads. One of the hosted […]\\nThe post D-Orbit ION Space Tug Hosts In-Orbit Refueling Demo appeared first on European Spaceflight."

        )
    )
}