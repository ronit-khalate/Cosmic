package com.ronit.cosmic.feature_auth.domain.repository

import com.ronit.cosmic.feature_auth.domain.model.AuthResponse
import com.ronit.cosmic.feature_auth.domain.model.UserInfo

interface UserRepository {

    suspend fun registerUser(userInfo: UserInfo,onResult: (AuthResponse) -> Unit)

    suspend fun signIn(email:String,password:String,onResult:(AuthResponse)->Unit)
}