package com.example.tickety

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tickety.navigation.AppNavigation
import com.example.tickety.ui.theme.TicketyTheme
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.initialize

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Firebase.initialize(this)
        val analytics = Firebase.analytics


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
