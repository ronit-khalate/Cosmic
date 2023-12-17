package com.ronit.cosmic.feature_auth.data

import com.ronit.cosmic.feature_auth.data.firebase.FirebaseAuthService
import com.ronit.cosmic.feature_auth.domain.model.AuthResponse
import com.ronit.cosmic.feature_auth.domain.model.UserInfo
import com.ronit.cosmic.feature_auth.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val firebaseAuthService: FirebaseAuthService
):UserRepository {
    override suspend fun registerUser(userInfo: UserInfo,onResult: (AuthResponse) -> Unit) {
        firebaseAuthService.registerUser(userInfo, onResult)
    }

    override suspend fun signIn(email: String, password: String,onResult:(AuthResponse)->Unit) {
         firebaseAuthService.signInUser(email,password,onResult)
    }
}