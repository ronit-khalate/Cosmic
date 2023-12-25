package com.ronit.cosmic.storage_feature.presentation

import android.content.Intent
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ronit.cosmic.R
import com.ronit.cosmic.core.utility.Screen
import com.ronit.cosmic.feature_feed.domain.Article
import com.ronit.cosmic.feature_feed.presentation.ArticleCard
import com.ronit.cosmic.feature_feed.presentation.FeedViewModel
import com.ronit.cosmic.storage_feature.domain.SavedArticle
import java.net.URLEncoder
import java.nio.charset.StandardCharsets



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageScreen(
    viewModel: StorageFeatureViewModel= hiltViewModel(),
    navController:NavController
){

    val articles = viewModel.savedArticles.collectAsState()

    Scaffold(

            topBar = { StorageScreenTopBar()}
    ) {

        LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                    items = articles.value,
                    key = { it.id }
            ) {

                SavedArticleCard(article = it, openNews = {
                    val encodedUrl = URLEncoder.encode(
                            it.newsUrl,
                            StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate(route = "${Screen.WebScreen.route}/$encodedUrl")
                }) {
                    viewModel.removeArticle(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedArticleCard(
    article: SavedArticle,
    openNews:()->Unit,
    onRemove:()->Unit
){
    val context= LocalContext.current

    var expanded by remember{ mutableStateOf(false) }
    val density= LocalDensity.current
    var isNewsOpenedInWeb by remember{ mutableStateOf(false) }

    Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(160.dp)
                .padding(top=5.dp, bottom = 5.dp),
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
                        model = ImageRequest.Builder(LocalContext.current)
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


                IconButton(onClick = {

                    Intent(Intent.ACTION_SEND).also {

                        it.putExtra(Intent.EXTRA_TEXT, article.newsUrl)
                        it.putExtra(Intent.EXTRA_SUBJECT, article.title)
                        it.setType("text/plain")
                        context.startActivity(Intent.createChooser(it, "Share via"))
                    }


                }) {

                    Image(imageVector = Icons.Default.Share, contentDescription = "Share", colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground))
                }

                IconButton(onClick =onRemove) {
                    Image(
                            painter = painterResource(id = R.drawable.bookmark_filled) ,
                            contentDescription ="Save",
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
            }






        }




    }
}