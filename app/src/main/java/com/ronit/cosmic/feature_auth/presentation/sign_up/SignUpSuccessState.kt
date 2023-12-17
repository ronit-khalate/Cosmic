package com.ronit.cosmic.feature_auth.presentation.sign_up

import android.view.textclassifier.ConversationActions.Message

sealed class SignUpSuccessState{

    object Success:SignUpSuccessState()

    // Default state means that signup has not been initialized
    object Default:SignUpSuccessState()
    data class Error(var message: String):SignUpSuccessState()
}
