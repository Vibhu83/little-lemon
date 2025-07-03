package com.vibhu.littlelemon.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vibhu.littlelemon.ui.composables.Onboarding
import com.vibhu.littlelemon.ui.keys.ApplicationKeys
import com.vibhu.littlelemon.ui.keys.LoginKeys
import com.vibhu.littlelemon.ui.composables.Home
import com.vibhu.littlelemon.ui.composables.Profile
import com.vibhu.littlelemon.ui.room.LittleLemonDatabase
import com.vibhu.littlelemon.ui.viewmodels.MenuViewModel

@Composable
fun Navigation(modifier: Modifier, navController: NavHostController){
    val context = LocalContext.current
    val preferences = context.getSharedPreferences(
        ApplicationKeys.preferences,
        Context.MODE_PRIVATE
    )
    val userIsLoggedIn = preferences.getBoolean(LoginKeys.userIsLoggedIn, false)


    NavHost(
        navController,
        startDestination =
            if (userIsLoggedIn)
                Destinations.Home.route
            else
                Destinations.Onboarding.route,
        modifier = modifier

    ){
        composable(route = Destinations.Home.route) {
            Home(
                navController,
            )
        }
        composable(route = Destinations.Profile.route) {
            Profile(navController)
        }
        composable(route = Destinations.Onboarding.route) {
            Onboarding(navController)
        }
    }
}