package com.ronit.cosmic.feature_auth.presentation.sign_up

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ronit.cosmic.util.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel= hiltViewModel(),
    navController: NavController,
){

    var photoUri : Uri? by remember {mutableStateOf(null)}
    var painter : Painter? by remember {mutableStateOf(null)}
    val launcher = rememberLauncherForActivityResult(

            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {

                it?.let {
                    photoUri=it
                }
            }

    )

    LaunchedEffect(key1 = signUpViewModel.signUpSuccessState){
        if (signUpViewModel.signUpSuccessState==SignUpSuccessState.Success){
            navController.navigate(Screen.Home.route)
        }
    }

    Scaffold(
            topBar = {
                TopBar {

                    navController.popBackStack()
                }
            }
    ) { it ->
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                        text = "Create Your Account",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                )
                Text(
                        text = "Please enter info to create account",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Surface(
                        modifier = Modifier
                            .size(140.dp)
                            .padding(5.dp),
                        shape = CircleShape,
                        border = BorderStroke(0.5.dp, Color.LightGray),
                        onClick = {
                            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ) {


                    if(photoUri!=null){
                        Log.d("signup","$photoUri")
                        painter = rememberAsyncImagePainter(
                                model = ImageRequest
                                    .Builder(LocalContext.current)
                                    .data(data = photoUri)
                                    .build()


                        )

                        signUpViewModel.onInfoEntered(UserInfoEvent.PhotoSelected(photoUri))

                        Image(
                                painter = painter as AsyncImagePainter,
                                contentDescription = "",
                                modifier = Modifier.size(135.dp),
                                contentScale = ContentScale.Crop)


                    }



                }

            Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                        value =signUpViewModel.userInfoState.displaName,
                        leadingIcon = {
                            Icon(
                                    imageVector = Icons.Outlined.Person,
                                    contentDescription ="Fist Name"
                            )
                        },
                        label = { Text(text = "Enter Display Name")},
                        onValueChange = {
                            signUpViewModel.onInfoEntered(UserInfoEvent.EnteredDisplayName(it))
                        }
                )
            Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                        value =signUpViewModel.userInfoState.email ,
                        leadingIcon = {
                            Icon(
                                    imageVector = Icons.Outlined.Email,
                                    contentDescription ="Email"
                            )
                        },
                        label = { Text(text = "Enter Email")},
                        onValueChange = {
                            signUpViewModel.onInfoEntered(UserInfoEvent.EnteredEmail(it))
                        }
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                        value =signUpViewModel.userInfoState.password,
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                    imageVector = Icons.Outlined.Lock,
                                    contentDescription ="Password"
                            )
                        },
                        label = { Text(text = "Enter Password")},
                        trailingIcon = {
                            Text(text = "Show")
                        },
                        onValueChange = {
                            signUpViewModel.onInfoEntered(UserInfoEvent.EnteredPassword(it))
                        }
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                        value =signUpViewModel.userInfoState.confirmedPassword,
                        leadingIcon = {
                            Icon(
                                    imageVector = Icons.Outlined.Lock,
                                    contentDescription ="Confirm Password"
                            )
                        },
                        label = { Text(text = "Confirm Password")},
                        trailingIcon = {
                           Text(text = "Show")
                        },
                        onValueChange = {
                            signUpViewModel.onInfoEntered(UserInfoEvent.EnteredConfirmedPassword(it))
                        }
                )
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(60.dp),
                        onClick = { signUpViewModel.onRegister() },

                ) {
                    Text("Register")
                }
            }
        }
    }
}

@Composable
fun TopBar(
    onBackClick:()->Unit
){
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
    ){
        Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Go Back",
                        modifier = Modifier
                            .size(30.dp)
                )
            }
        }
        Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Sign Up", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
        ) {

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun preview(){
//    SignUpScreen(){}
}