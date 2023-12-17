package com.ronit.cosmic.feature_auth.presentation.sign_up

import android.net.Uri

sealed class UserInfoEvent {

    data class EnteredDisplayName(var displayName:String):UserInfoEvent()
    data class EnteredEmail(var email:String):UserInfoEvent()
    data class EnteredPassword(var password:String):UserInfoEvent()
    data class EnteredConfirmedPassword(val confirmedPassword:String):UserInfoEvent()
    data class PhotoSelected(val photoUri: Uri?):UserInfoEvent()


}