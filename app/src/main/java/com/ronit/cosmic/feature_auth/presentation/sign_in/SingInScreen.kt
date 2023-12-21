package com.ronit.cosmic.feature_auth.presentation.sign_in

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ronit.cosmic.R
import com.ronit.cosmic.feature_auth.domain.model.AuthResponse
import com.ronit.cosmic.feature_auth.presentation.sign_in.state.SignInEvent
import com.ronit.cosmic.core.utility.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier:Modifier=Modifier,
    signInViewModel: SignInViewModel = hiltViewModel(),
    googleAuthUiClient: GoogleAuthUiClient,
    navController:NavController

){

    val coroutineScope= rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = {result->

                if(result.resultCode== ComponentActivity.RESULT_OK){

                    coroutineScope.launch {

                        val signInResult = googleAuthUiClient.getSignInWithIntent(
                                intent=result.data?: return@launch
                        )

                        signInResult.data?.let {userData->

                            if(signInViewModel.requireAdditionalInfo.isAdditionInfoRequired){

                                signInViewModel.requireAdditionalInfo=signInViewModel.requireAdditionalInfo.copy(
                                        isAdditionInfoRequired = true
                                )
                            }
                            else
                            {

                                navController.popBackStack()
                                navController.navigate(Screen.Home.route)
                            }
                        }

                    }

                }
            }
    )

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember{ SnackbarHostState()}

    LaunchedEffect(key1 = signInViewModel.requireAdditionalInfo){
        if(signInViewModel.requireAdditionalInfo.isAdditionInfoRequired){

        }
    }
    LaunchedEffect(key1 = signInViewModel.authAuthResponse){

        if(signInViewModel.authAuthResponse==AuthResponse.Success){
            navController.navigate(Screen.Home.route)
            navController.popBackStack(route = Screen.Home.route, inclusive = false)
        }

    }

    Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
    ) {

        Column(
                modifier = modifier
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            Text(
                    text = "Welcome To Cosmic",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedContent(
                    targetState = signInViewModel.requireAdditionalInfo.isAdditionInfoRequired, label = ""
            ) { state ->

                if (state) {
                    AdditionalInfoScreen(signInViewModel = signInViewModel){
                        navController.navigate(Screen.Home.route)
                        navController.popBackStack(route = Screen.Home.route, inclusive = false)
                    }
                }
                else{
                    Card(
                            modifier = Modifier
                                .width(340.dp),
                            shape = RoundedCornerShape(size = 10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                                modifier = Modifier
                                    .padding(all = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,

                                    ) {
                                Text(
                                        text = "Sign in",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold
                                )
                                Text(
                                        text = "Start Exploring Space",
                                        style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))


                            Column {

                                OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        singleLine = true,
                                        supportingText = {

                                            if (signInViewModel.authAuthResponse::class == AuthResponse.Error::class) {
                                                Text(text = (signInViewModel.authAuthResponse as AuthResponse.Error).message)
                                            }
                                        },
                                        value = signInViewModel.state.email,
                                        onValueChange = { email ->
                                            signInViewModel.onSignInEvent(
                                                    SignInEvent.EnteredEmail(email)
                                            )
                                        },
                                        label = { Text(text = "Email") },
                                        shape = RoundedCornerShape(size = 5.dp)
                                )
                                OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        value = signInViewModel.state.password,
                                        visualTransformation = signInViewModel.state.visibility,
                                        singleLine = true,
                                        onValueChange = { password ->
                                            signInViewModel.onSignInEvent(
                                                    SignInEvent.EnteredPassword(
                                                            password
                                                    )
                                            )
                                        },
                                        trailingIcon = {
                                            Text(
                                                    modifier = Modifier
                                                        .padding(end = 10.dp)
                                                        .clickable {
                                                            signInViewModel.onSignInEvent(
                                                                    SignInEvent.ChangedPasswordVisibility
                                                            )
                                                        },
                                                    text = if (signInViewModel.state.visibility == VisualTransformation.None) "Hide" else "Show",
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.Bold
                                            )
                                        },
                                        label = { Text(text = "Password") },
                                        shape = RoundedCornerShape(size = 5.dp),
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }



                            Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start,
                            ) {
                                Text(
                                        modifier = Modifier
                                            .clickable { },
                                        text = "Forgot Password?",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    onClick = {
                                        signInViewModel.onSignInEvent(SignInEvent.OnSignIn)
                                        if(signInViewModel.authAuthResponse::class == AuthResponse.Error::class){
                                            scope.launch {

                                                snackBarHostState.showSnackbar((signInViewModel.authAuthResponse as AuthResponse.Error).message)
                                            }
                                        }
                                        else if(signInViewModel.authAuthResponse==AuthResponse.Success){
                                            navController.navigate(Screen.Home.route)
                                            navController.popBackStack(route = Screen.Home.route, inclusive = false)
                                        }

                                    }
                            ) {
                                Text(text = "Sign in")
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "or")
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = MaterialTheme.colorScheme.onBackground
                                    ),


                                    onClick = {
                                        coroutineScope.launch {
                                            val signInIntentSender=googleAuthUiClient.signIn()
                                            launcher.launch(
                                                    IntentSenderRequest.Builder(
                                                            signInIntentSender?:return@launch
                                                    ).build()
                                            )
                                        }
                                    }
                            ) {

                                Image(
                                        imageVector = ImageVector.vectorResource(R.drawable.google_icon),
                                        contentDescription = "",
                                        Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(text = "Sign in with Google!!")
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                    modifier = Modifier
                                        .clickable {navController.navigate(Screen.SignUp.route) },
                                    text = "Create Account Using Email!!",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                            )

                        }
                    }
                }
            }



            }


    }

}

//@Composable
//@SignInScreenPreview
//private fun Preview(){
//    SignInScreen(modifier = Modifier.fillMaxSize(), onCreateAccountWithEmail = {}, onSignInWithGoogle = {})
//}


@Preview(
        name = "Sign In Screen",
        showBackground = true

)
annotation class SignInScreenPreview