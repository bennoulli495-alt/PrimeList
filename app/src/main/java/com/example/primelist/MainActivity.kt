package com.example.primelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.primelist.ui.theme.PrimeListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrimeListTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PrimeListNavHost()
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun PrimeListNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            // HomeScreen will be added in a later file
            // HomeScreen(navController = navController)
        }
        composable("profile") {
            // ProfileScreen will be added in a later file
            // ProfileScreen(navController = navController)
        }
    }
}
