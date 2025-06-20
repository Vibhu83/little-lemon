package com.vibhu.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vibhu.littlelemon.ui.composables.Onboarding
import com.vibhu.littlelemon.ui.keys.ApplicationKeys
import com.vibhu.littlelemon.ui.keys.LoginKeys
import com.vibhu.littlelemon.ui.navigation.Destinations
import com.vibhu.littlelemon.ui.composables.Home
import com.vibhu.littlelemon.ui.composables.Profile
import com.vibhu.littlelemon.ui.navigation.Navigation
import com.vibhu.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonTheme {
                val preferences = LocalContext.current.getSharedPreferences(
                    ApplicationKeys.preferences,
                    Context.MODE_PRIVATE
                )
                val navController = rememberNavController()
                val userIsLoggedIn = preferences.getBoolean(LoginKeys.userIsLoggedIn, false)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(modifier = Modifier.fillMaxSize().padding(innerPadding), navController)
                }
            }
        }
    }
}

@Composable
fun Greeting( modifier: Modifier = Modifier) {
    Text(
        text = "Hello World",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LittleLemonTheme {
        Greeting()
    }
}