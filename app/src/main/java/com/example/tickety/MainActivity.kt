package com.example.tickety

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tickety.navigation.AppNavigation
import com.example.tickety.ui.theme.TicketyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val loggedInUserId: Long = sharedPreferences.getLong("logged_in_user_id", 0L)

        setContent {
            TicketyTheme {
                AppNavigation(userId = loggedInUserId)
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
