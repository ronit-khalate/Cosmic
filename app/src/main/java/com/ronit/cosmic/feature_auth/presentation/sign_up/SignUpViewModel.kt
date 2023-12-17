package com.ronit.cosmic.feature_auth.presentation.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.cosmic.feature_auth.data.UserRepositoryImpl
import com.ronit.cosmic.feature_auth.domain.model.AuthResponse
import com.ronit.cosmic.feature_auth.domain.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
        val registerUserRepositoryImpl: UserRepositoryImpl
):ViewModel() {

    private var _pickImageState by mutableStateOf(false)
    val pickImageState =_pickImageState

    var signUpSuccessState:SignUpSuccessState by mutableStateOf(SignUpSuccessState.Default)

    var userInfoState by mutableStateOf(UserInfoState())
        private set

    var painter :Painter?=null

    fun onInfoEntered(event: UserInfoEvent){

        when(event){

            is UserInfoEvent.EnteredDisplayName->{
                userInfoState=userInfoState.copy(displaName = event.displayName)
            }

            is UserInfoEvent.EnteredEmail->{
                userInfoState=userInfoState.copy(email = event.email)
            }

            is UserInfoEvent.EnteredPassword ->{
                userInfoState=userInfoState.copy(password = event.password)
            }

            is UserInfoEvent.EnteredConfirmedPassword->{
                userInfoState=userInfoState.copy(confirmedPassword = event.confirmedPassword)
            }

            is UserInfoEvent.PhotoSelected ->{
                userInfoState=userInfoState.copy(photoUri = event.photoUri)
            }

        }
    }
    fun updatePickImageState(){

        _pickImageState=true
    }

    fun onRegister(){

        viewModelScope.launch {

            registerUserRepositoryImpl.registerUser(
                    userInfo = UserInfo(
                            displayName = userInfoState.displaName,
                            email = userInfoState.email,
                            password = userInfoState.password,
                            photoUri = userInfoState.photoUri
                    ),
                    onResult = {authResponse ->

                        if(authResponse==AuthResponse.Success){
                            signUpSuccessState=SignUpSuccessState.Success
                        }
                        else if (authResponse::class== AuthResponse.Error::class){

                            signUpSuccessState=SignUpSuccessState.Error((authResponse as AuthResponse.Error).message)
                        }

                    }
            )
        }
    }
}