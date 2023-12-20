package com.ronit.cosmic.core.presentation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ronit.cosmic.Home
import com.ronit.cosmic.feature_auth.domain.model.UserInfo
import com.ronit.cosmic.feature_auth.presentation.sign_in.GoogleAuthUiClient
import com.ronit.cosmic.feature_auth.presentation.sign_in.SignInScreen
import com.ronit.cosmic.feature_auth.presentation.sign_in.SignInViewModel
import com.ronit.cosmic.feature_auth.presentation.sign_up.SignUpScreen
import com.ronit.cosmic.feature_feed.presentation.FeedScreen
import com.ronit.cosmic.util.Screen
import kotlinx.coroutines.launch

@Composable
fun Navigation(
    googleAuthUiClient:GoogleAuthUiClient,

){
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current
    Log.d("userR","${googleAuthUiClient.getSignedInUser()?.userId}")
    NavHost(
            navController = navController,
            startDestination =if(googleAuthUiClient.getSignedInUser()!=null){ Screen.Home.route }else{ Screen.SignIn.route}

    ){

        composable(route= Screen.SignIn.route){

            SignInScreen(
                    modifier = Modifier.fillMaxSize(),
                    googleAuthUiClient = googleAuthUiClient,
                    navController=navController
            )
        }

        composable(route=Screen.SignUp.route){

            SignUpScreen(navController = navController)
        }

        composable(
                route= Screen.Home.route,
        ){

            FeedScreen()
        }
    }
}