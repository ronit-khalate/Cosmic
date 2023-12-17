package com.ronit.cosmic.feature_auth.presentation.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ronit.cosmic.R
import com.ronit.cosmic.feature_auth.domain.SignInResult
import com.ronit.cosmic.feature_auth.domain.UserData
import com.ronit.cosmic.feature_auth.domain.model.UserInfo
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject


class GoogleAuthUiClient constructor(
        private val context: Context,
        private val oneTapSignInClient: SignInClient
) {
       private val auth = Firebase.auth


        fun isSignedIn():Boolean{

            return auth.currentUser!=null
        }

        suspend fun signIn():IntentSender?{


                val result = try {
                    oneTapSignInClient.beginSignIn(buildSignInRequest())
                            .await()
                }catch (e:Exception){
                        e.printStackTrace()
                        if(e is CancellationException) throw e
                        null

                }

                return result?.pendingIntent?.intentSender
        }

        suspend fun getSignInWithIntent(intent: Intent): SignInResult {

            val credential = oneTapSignInClient.getSignInCredentialFromIntent(intent)
            val googleIdToke = credential.googleIdToken
            val googleCredential = GoogleAuthProvider.getCredential(googleIdToke,null)

            return try {

                val user = auth.signInWithCredential(googleCredential)
                    .await()
                    .user
                SignInResult(
                        data = user?.run {

                            UserData(
                                    userId = uid,
                                    userName = displayName,
                                    email =email,
                                    profilePictureUrl = photoUrl
                            )
                        },
                        errorMessage = null
                )
            }catch (e:Exception){
                e.printStackTrace()
                if(e is CancellationException) throw e
                SignInResult(
                        data = null,
                        errorMessage = e.message
                )
            }

        }

        suspend fun signOut(){
            try {
                oneTapSignInClient.signOut().await()
                auth.signOut()
            }catch (e:Exception){

                e.printStackTrace()
                if (e is CancellationException) throw e
            }
        }

    fun getSignedInUser() :UserData? = auth.currentUser?.run {

        UserData(
                userId = uid,
                userName = this.displayName,
                email =this.email,
                profilePictureUrl = photoUrl
        )
    }

    fun setUserNameForGoogleSignInAccount(userName:String,onResult:()->Unit){

        val userNameUpdateRequest = userProfileChangeRequest {

            this.displayName=userName
        }
        auth.currentUser!!.updateProfile(userNameUpdateRequest)
            .addOnCompleteListener {task->
                onResult()
            }
    }

    private fun buildSignInRequest():BeginSignInRequest{

            return BeginSignInRequest.Builder()
                    .setGoogleIdTokenRequestOptions(
                            GoogleIdTokenRequestOptions.Builder()
                                    .setSupported(true)
                                    .setFilterByAuthorizedAccounts(false)
                                    .setServerClientId(context.getString(R.string.web_client_id))
                                    .build()
                    )
                    // if have only one acc it will automatically select that one
                    .setAutoSelectEnabled(true)
                    .build()
    }



}