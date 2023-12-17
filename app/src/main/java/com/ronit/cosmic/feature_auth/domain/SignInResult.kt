package com.ronit.cosmic.feature_auth.domain

import android.net.Uri

data class SignInResult(
        val data:UserData?,
        val errorMessage:String?
)

data class UserData(
        val userId:String,
        val userName:String?,
        val email:String?,
        val profilePictureUrl: Uri?
)
