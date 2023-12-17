package com.ronit.cosmic.feature_auth.presentation.sign_in.state

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.ronit.cosmic.feature_auth.domain.UserData


data class SignInState(
    var email:String="",
    var password:String="",
    var visibility:VisualTransformation=PasswordVisualTransformation()
)