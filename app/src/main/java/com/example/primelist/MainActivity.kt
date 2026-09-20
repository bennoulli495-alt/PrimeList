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
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.primelist.ui.screens.AnalyticsScreen
import com.example.primelist.ui.screens.TemplatesScreen
import com.example.primelist.ui.screens.CategoriesListScreen

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
    var showAnalytics by remember { mutableStateOf(false) }
    var showTemplates by remember { mutableStateOf(false) }
    var showCategories by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
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
                        },
                        onAnalyticsClick = {
                            scope.launch { drawerState.close() }
                            showAnalytics = true
                        },
                        onTemplatesClick = {
        scope.launch { drawerState.close() }
        showTemplates = true
    },
    onCategoriesClick = {
        scope.launch { drawerState.close() }
        showCategories = true
    }
)
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

        if (showAnalytics) {
            AnalyticsScreen(onBackClick = { showAnalytics = false })
        }
        if (showTemplates) {
    TemplatesScreen(onBackClick = { showTemplates = false })
}

if (showCategories) {
    CategoriesListScreen(onBackClick = { showCategories = false })
}
    }
}
