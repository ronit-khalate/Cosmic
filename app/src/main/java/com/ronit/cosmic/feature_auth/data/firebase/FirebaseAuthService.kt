package com.ronit.cosmic.feature_auth.data.firebase

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.ronit.cosmic.feature_auth.domain.model.AuthResponse
import com.ronit.cosmic.feature_auth.domain.model.UserInfo
import javax.inject.Inject

class FirebaseAuthService @Inject constructor() {
    companion object {

        val auth = Firebase.auth
        val storage = Firebase.storage
        val database =Firebase.database.reference
    }

    fun registerUser(userInfo: UserInfo,onResult: (AuthResponse) -> Unit){
       auth.createUserWithEmailAndPassword(userInfo.email,userInfo.password!!)
           .addOnCompleteListener {task->

               if(task.isSuccessful){
                   Log.d("res","${task.result.user?.uid}")
                   database.child("userInfo").child(auth.currentUser!!.uid).child("displayName").setValue(userInfo.displayName)
                   val user=task.result.user!!

                   val profileUpdates = userProfileChangeRequest {
                       this.displayName=userInfo.displayName
                       this.photoUri=userInfo.photoUri
                   }

                   user.updateProfile(profileUpdates)
                       .addOnCompleteListener {task->
                           onResult(AuthResponse.Success)
                       }
                       .addOnFailureListener {exception->
                           onResult(AuthResponse.Error(exception.message!!))
                       }

               }


           }


    }

    fun signInUser(email:String,password:String,onResult:(AuthResponse)->Unit){

        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {task->

                    if(task.isSuccessful){



                        onResult(AuthResponse.Success)
                    }


                }
                .addOnFailureListener {
                    onResult(AuthResponse.Error(message = "Sign In Failed"))
                }
                .addOnCanceledListener {
                    onResult(AuthResponse.Error(message = "Sign In Canceled"))
                }
        }
        catch (e:IllegalArgumentException){
            onResult(AuthResponse.Error("Wrong Input"))
        }

    }
}