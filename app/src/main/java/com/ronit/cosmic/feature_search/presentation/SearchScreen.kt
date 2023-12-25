package com.ronit.cosmic.feature_search.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ronit.cosmic.feature_search.domain.model.ImageInfo


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
       ExperimentalComposeUiApi::class
)
@Composable
fun SearchScreen(){
    val viewModel: SearchScreenViewModel= hiltViewModel()
    var searchBtnPressed by remember{ mutableStateOf(false)}

    val keyboardController=LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = viewModel.searchTypedValue){
        if(viewModel.searchTypedValue.isEmpty()){
            viewModel.clearImageList()
        }
    }




    Scaffold() {

        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    value = viewModel.searchTypedValue,
                    onValueChange ={
                        viewModel.onSearchKeywordEntered(it)
                   },
                    shape = RoundedCornerShape(30.dp),
                    placeholder = { Text(
                            modifier=Modifier
                                .padding(start = 10.dp),
                            text = "Search",
                            fontSize = 20.sp
                    )},
                    leadingIcon = { Image(
                            modifier=Modifier
                                .padding(start = 10.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                    )},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                            onSearch = {
                                viewModel.getImageInfoByKeyword(viewModel.searchTypedValue)
                                searchBtnPressed=true
                                keyboardController?.hide()
                            }
                    )

                    
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Images")
            Spacer(modifier = Modifier.height(10.dp))


            val lazyRowState = rememberLazyListState()
            val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyRowState)

            LaunchedEffect(key1 = viewModel.images){
                lazyRowState.scrollToItem(0)
                searchBtnPressed=false
            }

            if(viewModel.images.isEmpty() && searchBtnPressed){
                CircularProgressIndicator()
            }
            else {


                LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = lazyRowState,
                        flingBehavior = flingBehavior
                ) {

                    items(
                            items = viewModel.images,
                            key = { it.hashCode() }

                    ) {
                        SearchImageCard(it)
                    }

                }
            }



        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchImageCard(imageInfo: ImageInfo){

    val deviceWidth = LocalConfiguration.current.screenWidthDp
    Card(
            modifier = Modifier
                .width(deviceWidth.dp - 25.dp)
                .padding(start = 2.5.dp, end = 2.5.dp)
                .height(230.dp),
            shape = RoundedCornerShape(30.dp),
            onClick = { /*TODO*/ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = imageInfo.thumbnailUri,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = imageInfo.title
            )

            Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp, start = 10.dp, end = 10.dp)
                    ,
                    maxLines = 2,
                    color = Color.White,
                    text = imageInfo.title,
                    fontSize = 25.sp
            )
        }
    }
}

