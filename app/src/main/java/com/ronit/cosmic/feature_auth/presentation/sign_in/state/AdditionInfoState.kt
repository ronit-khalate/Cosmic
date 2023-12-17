package com.ronit.cosmic.feature_auth.presentation.sign_in.state

data class AdditionInfoState(
        var isAdditionInfoRequired:Boolean=false,
        var userName:String="",
        var isUserNameRequired:Boolean=true,
        var isInfoUpdatedSuccessFully:Boolean=false
)
