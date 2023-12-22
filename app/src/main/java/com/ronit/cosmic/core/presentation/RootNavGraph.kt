package com.ronit.cosmic.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ronit.cosmic.core.presentation.components.BottomBar

import com.ronit.cosmic.feature_auth.presentation.sign_in.GoogleAuthUiClient
import com.ronit.cosmic.feature_auth.presentation.sign_in.SignInScreen
import com.ronit.cosmic.feature_auth.presentation.sign_up.SignUpScreen
import com.ronit.cosmic.feature_feed.presentation.FeedScreen
import com.ronit.cosmic.core.utility.Screen
import com.ronit.cosmic.feature_feed.presentation.WebPAgeScreen
import com.ronit.cosmic.storage_feature.presentation.StorageScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    googleAuthUiClient:GoogleAuthUiClient,

){

    val navController = rememberNavController()
    Scaffold(
            bottomBar = { BottomBar(navController)}
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            NavHost(
                    navController = navController,
                    startDestination =if(googleAuthUiClient.getSignedInUser()!=null){ Screen.Home.route }else{ Screen.SignIn.route}

            ){

                composable(route= Screen.SignIn.route){

                    SignInScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 30.dp),
                            googleAuthUiClient = googleAuthUiClient,
                            navController=navController
                    )
                }

                composable(route= Screen.SignUp.route){

                    SignUpScreen(navController = navController)
                }

                composable(
                        route= Screen.Home.route,
                ){

                    FeedScreen(navController=navController)
                }

                composable(
                        route= Screen.WebScreen.routeWithArg,
                        arguments = Screen.WebScreen.argument
                ){
                    WebPAgeScreen(newsUrl = it.arguments?.getString(Screen.WebScreen.newsUrlArgument)?:"") {
                        navController.popBackStack()
                    }
                }

                composable(
                        route=Screen.StorageScreen.route
                ){
                    StorageScreen(navController = navController)
                }
            }
        }

    }

}