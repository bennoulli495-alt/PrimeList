package com.example.primelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.primelist.ui.screens.HomeScreen
import com.example.primelist.ui.screens.ProfileScreen
import com.example.primelist.ui.theme.DarkNavyBackground
import com.example.primelist.ui.theme.PrimeListTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrimeListTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PrimeListApp()
                }
            }
        }
    }
}

@Composable
fun PrimeListApp() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = DarkNavyBackground,
                modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight()
            ) {
                ProfileScreen(
                    onBackClick = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        HomeScreen(
            onMenuClick = {
                scope.launch { drawerState.open() }
            }
        )
    }
}
