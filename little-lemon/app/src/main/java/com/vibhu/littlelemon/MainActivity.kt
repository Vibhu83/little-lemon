package com.vibhu.littlelemon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.vibhu.littlelemon.ui.keys.ApplicationKeys
import com.vibhu.littlelemon.ui.navigation.Navigation
import com.vibhu.littlelemon.ui.room.LittleLemonDatabase
import com.vibhu.littlelemon.ui.theme.LittleLemonTheme
import com.vibhu.littlelemon.ui.viewmodels.MenuViewModel

class MainActivity : ComponentActivity() {

    private lateinit var database: LittleLemonDatabase
    private lateinit var menuViewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonTheme(darkTheme = false) {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(modifier = Modifier.fillMaxSize().padding(innerPadding), navController)
                }
            }
        }
    }
}
