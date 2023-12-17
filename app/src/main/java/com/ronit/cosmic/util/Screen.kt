package com.ronit.cosmic.util

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class Screen(val route:String)
{
    object SignIn:Screen("SignInScreen")
    object Home:Screen("HomeScreen"){

        const val userIdArg="userid"
        val routeWithArgs = "${this.route}/{userid}"
        val arguments = listOf(
                navArgument(name = this.userIdArg){
                    type= NavType.StringType
                }
        )
    }

    object SignUp:Screen("SignUpScreen"){

    }

}

