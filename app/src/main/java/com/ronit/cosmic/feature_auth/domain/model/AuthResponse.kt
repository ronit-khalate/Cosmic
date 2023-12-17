package com.ronit.cosmic.feature_auth.domain.model

sealed class AuthResponse {
    object Success:AuthResponse()
    data class Error(val message:String):AuthResponse()
    object default:AuthResponse()
}