package com.ronit.cosmic.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ronit.cosmic.R


@Preview(showBackground = true)
@Composable
fun FeedScreenTopBar(){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(color = MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
    ){

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                    modifier=Modifier
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.cosmic_logo),
                    contentDescription = "cosmic",
                    tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.7f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                    text ="cosmic",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalAlignment =Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    }
}