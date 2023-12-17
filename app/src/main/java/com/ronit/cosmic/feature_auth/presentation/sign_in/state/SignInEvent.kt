package com.ronit.cosmic.feature_auth.presentation.sign_in.state

import com.ronit.cosmic.feature_auth.domain.UserData

sealed class SignInEvent {

    data class EnteredEmail(val email:String):SignInEvent()
    data class EnteredPassword(val password:String):SignInEvent()
    object ChangedPasswordVisibility:SignInEvent()
    object OnSignIn:SignInEvent()
}