package com.ronit.cosmic.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ronit.cosmic.R
import com.ronit.cosmic.core.presentation.state.BottomBarButtonsState
import com.ronit.cosmic.core.utility.Screen

@Composable
fun BottomBar(navController: NavController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var selectedButtonState:BottomBarButtonsState by remember{ mutableStateOf(BottomBarButtonsState.ArticleButton) }
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        modifier = Modifier
            .height(50.dp),
        backgroundColor = MaterialTheme.colorScheme.primary

    ) {

        NavigationBarItem(
                enabled = currentDestination?.hierarchy?.any { it.route==Screen.Home.route }==false,
                selected = currentDestination?.hierarchy?.any { it.route==Screen.Home.route }==true,
                onClick = {
                    navController.navigate(Screen.Home.route){

                        popUpTo(navController.graph.startDestinationId){
                            saveState=true
                        }
                        launchSingleTop=true
                        restoreState=true
                    }
                },
                icon = {
                    Image(
                            painter = painterResource(id = R.drawable.article),
                            contentDescription ="Articles",
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
        )
        NavigationBarItem(
                enabled = currentDestination?.hierarchy?.any { it.route==Screen.StorageScreen.route }==false,
                selected = currentDestination?.hierarchy?.any { it.route==Screen.StorageScreen.route }==true,
                onClick = {
                    navController.navigate(Screen.StorageScreen.route){

                        popUpTo(navController.graph.startDestinationId){
                            saveState=true
                        }
                        launchSingleTop=true
                        restoreState=true
                    }

              },
                icon = {
                    Image(
                            painter = painterResource(id = R.drawable.bookmarks),
                            contentDescription ="Bookmarks",
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
        )
    }
}