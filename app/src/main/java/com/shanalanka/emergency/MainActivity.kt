package com.shanalanka.emergency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.shanalanka.emergency.ui.navigation.NavGraph
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the SahanaLanka Emergency Alert application.
 * 
 * This activity serves as the entry point for the app and sets up
 * the navigation and theme.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SahanaLankaTheme {
                SahanaLankaApp()
            }
        }
    }
}

@Composable
fun SahanaLankaApp() {
    val navController = rememberNavController()
    
    NavGraph(
        navController = navController,
        modifier = Modifier.fillMaxSize()
    )
}
