package com.vibhu.littlelemon.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vibhu.littlelemon.ui.composables.Onboarding
import com.vibhu.littlelemon.ui.keys.ApplicationKeys
import com.vibhu.littlelemon.ui.keys.LoginKeys
import com.vibhu.littlelemon.ui.composables.Home
import com.vibhu.littlelemon.ui.composables.Profile

@Composable
fun Navigation(modifier: Modifier, navController: NavHostController){
    val preferences = LocalContext.current.getSharedPreferences(
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
            Home()
        }
        composable(route = Destinations.Profile.route) {
            Profile(navController)
        }
        composable(route = Destinations.Onboarding.route) {
            Onboarding(navController)
        }
    }
}