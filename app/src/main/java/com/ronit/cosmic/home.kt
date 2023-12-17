package com.ronit.cosmic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Home(
    userID:String?,
    logout:()->Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { logout() }
    ) {
        Text(text = userID?:"null")
    }
}