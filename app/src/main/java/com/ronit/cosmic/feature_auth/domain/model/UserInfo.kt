package com.ronit.cosmic.feature_auth.domain.model

import android.net.Uri

data class UserInfo(
        val displayName:String,
        val email:String,
        val password:String?,
        val photoUri:Uri?=null
)
