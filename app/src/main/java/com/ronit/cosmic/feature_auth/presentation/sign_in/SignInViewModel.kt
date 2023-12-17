package com.ronit.cosmic.feature_auth.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronit.cosmic.feature_auth.data.UserRepositoryImpl
import com.ronit.cosmic.feature_auth.domain.model.AuthResponse
import com.ronit.cosmic.feature_auth.presentation.sign_in.state.AdditionInfoState
import com.ronit.cosmic.feature_auth.presentation.sign_in.state.SignInEvent
import com.ronit.cosmic.feature_auth.presentation.sign_in.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val authUiClient: GoogleAuthUiClient,
    val userRepositoryImpl: UserRepositoryImpl
):ViewModel() {


    var state by mutableStateOf(SignInState())
        private set
    var authAuthResponse:AuthResponse by mutableStateOf(AuthResponse.default)
        private set
    var requireAdditionalInfo by mutableStateOf(AdditionInfoState())


    fun onSignInEvent(event: SignInEvent){

        when(event){

            is SignInEvent.EnteredEmail->{
                state=state.copy(email = event.email)
            }
            is SignInEvent.EnteredPassword->{
                state=state.copy(password = event.password)
            }

            is SignInEvent.OnSignIn->{
                viewModelScope.launch{

                    userRepositoryImpl.signIn(state.email,state.password){signInResponse ->

                       authAuthResponse=signInResponse
                    }
                }
            }

            is SignInEvent.ChangedPasswordVisibility->{
                if(state.visibility== VisualTransformation.None)
                    state=state.copy(visibility = PasswordVisualTransformation())
                else
                    state=state.copy(visibility = VisualTransformation.None)
            }

            else->{}
        }
    }

    fun updateUserNameInGoogleSignIn(userName:String){

        authUiClient.setUserNameForGoogleSignInAccount(userName = userName){
            requireAdditionalInfo=requireAdditionalInfo.copy(isInfoUpdatedSuccessFully = true)
        }
    }



    init {


    }
}