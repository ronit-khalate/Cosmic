package com.ronit.cosmic.core.utility

import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class Screen(val route:String)
{
    object SignIn: Screen("SignInScreen")
    object Home: Screen("HomeScreen"){

        const val userIdArg="userid"
        val routeWithArgs = "${this.route}/{userid}"
        val arguments = listOf(
                navArgument(name = userIdArg){
                    type= NavType.StringType
                }
        )
    }

    object SignUp: Screen("SignUpScreen")

    object WebScreen:Screen("WebScreen"){

        const val newsUrlArgument ="newUrl"
        val routeWithArg = "${route}/{$newsUrlArgument}"
        val argument = listOf(
                navArgument(name= newsUrlArgument){
                    type= NavType.StringType
                }
        )
    }

}

