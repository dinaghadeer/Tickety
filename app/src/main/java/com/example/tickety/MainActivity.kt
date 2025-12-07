package com.example.tickety

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tickety.navigation.AppNavigation
import com.example.tickety.ui.theme.TicketyTheme
import androidx.compose.foundation.layout.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TicketyTheme {
                AppNavigation()

            }
        }
    }
}

//@Composable
//fun AppRoot() {
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        bottomBar = {
//            // You will put your BottomBar() here later
//        }
//    ) { innerPadding ->
//        AppNavigation(modifier = Modifier.padding(innerPadding))
//    }
//}
