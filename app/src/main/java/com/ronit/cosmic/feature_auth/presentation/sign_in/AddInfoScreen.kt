package com.ronit.cosmic.feature_auth.presentation.sign_in

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ronit.cosmic.R
import com.ronit.cosmic.feature_auth.domain.model.AuthResponse
import com.ronit.cosmic.feature_auth.presentation.sign_in.state.SignInEvent
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdditionalInfoScreen(
    signInViewModel: SignInViewModel,
    onSignIn:()->Unit
){

    Card(
            modifier = Modifier
                .width(340.dp),
            shape = RoundedCornerShape(size = 10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {

        Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp,top =10.dp),
                horizontalAlignment = Alignment.Start,

                ) {
            Text(
                    text = "Almost There",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
            )
            Text(
                    text = "Additional Info Needed",
                    style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
        ) {

        }
        Column(
                modifier = Modifier
                    .padding(all = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
        ) {


            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    supportingText = {

                    },
                    enabled = signInViewModel.requireAdditionalInfo.isUserNameRequired,
                    value = signInViewModel.requireAdditionalInfo.userName,
                    onValueChange = {
                        signInViewModel.requireAdditionalInfo=signInViewModel.requireAdditionalInfo
                            .copy(userName = it)
                    },

                    label = { Text(text = "User Name") },
                    shape = RoundedCornerShape(size = 5.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = signInViewModel.requireAdditionalInfo.userName.isNotEmpty(),
                    onClick = {

                        if(signInViewModel.requireAdditionalInfo.isInfoUpdatedSuccessFully){
                            onSignIn()
                        }
                    }
            ) {
                Text(text = "Sign in")
            }



        }


    }
}

@Preview(showBackground = true)
@Composable
private fun preview(){
//    AdditionalInfoScreen( null)
}