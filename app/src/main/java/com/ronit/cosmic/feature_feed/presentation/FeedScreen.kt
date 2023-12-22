package com.ronit.cosmic.feature_feed.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ronit.cosmic.R
import com.ronit.cosmic.core.utility.Screen
import com.ronit.cosmic.feature_feed.domain.Article
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel= hiltViewModel(),
    articles: LazyPagingItems<Article> = viewModel.articlePagingFlow.collectAsLazyPagingItems(),
    navController: NavController
){

    Scaffold (

            topBar = { FeedScreenTopBar() }
    ){


        LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(
                    count = articles.itemCount,
                    key = articles.itemKey { it.id },
                    contentType = articles.itemContentType { "contentType" }
            ) { index ->

                val item = articles[index]


                item?.let { article ->

                    ArticleCard(
                            article = article,
                            openNews = {
                                val encodedUrl = URLEncoder.encode(
                                        article.newsUrl,
                                        StandardCharsets.UTF_8.toString()
                                )
                                navController.navigate(route = "${Screen.WebScreen.route}/$encodedUrl")
                            },
                            viewModel
                    )
                }

            }
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(
    article:Article,
    openNews:()->Unit,
    viewModel: FeedViewModel
){

    var expanded by remember{ mutableStateOf(false) }
    val density= LocalDensity.current
    var isNewsOpenedInWeb by remember{ mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(article.isSaved) }

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
                       .heightIn(min = 160.dp),
                   shape = RoundedCornerShape(20.dp)
           ) {

            AsyncImage(
                    model =ImageRequest.Builder(LocalContext.current)
                        .data(article.imageUrl)
                        .crossfade(true)
                        .crossfade(10)
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
                        .fillMaxWidth(),
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
                                .clickable { 
                                    isNewsOpenedInWeb=!isNewsOpenedInWeb
                                    if(isNewsOpenedInWeb) {
                                        openNews()
                                    }
                                }
                    )
                }



            }

            Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
            ){

                IconButton(onClick ={

                        if(isSaved){
                            viewModel.removeArticle(article){
                                isSaved=false
                            }
                        }
                        else{
                            viewModel.saveArticle(article){
                                isSaved=true
                            }
                        }

                }) {
                    Image(
                            painter = if(isSaved) painterResource(id = R.drawable.bookmark_filled) else painterResource(id = R.drawable.bookmark_outlined),
                            contentDescription ="Save",
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
            }






        }




    }
}
