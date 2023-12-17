package com.ronit.cosmic.feature_auth.presentation.sign_up

import android.net.Uri

data class UserInfoState(
        var displaName:String="",
        var email:String="",
        var password:String="",
        var confirmedPassword:String="",
        var photoUri: Uri?=null
)