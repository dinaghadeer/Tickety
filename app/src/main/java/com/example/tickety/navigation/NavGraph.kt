package com.example.tickety.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.tickety.ui.screens.SplashScreen
import com.example.tickety.ui.screens.details.EventDetailsScreen
import com.example.tickety.ui.screens.details.sampleEvent
import com.example.tickety.ui.screens.events.EventsScreen
import com.example.tickety.ui.screens.mybookings.MyBookingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import model.AppDatabase
import com.example.tickety.ui.auth.*
import model.User


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // Observe current route
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    val currentUser = remember { mutableStateOf<User?>(null) }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.SplashScreen.route && currentRoute != Screen.LoginScreen.route && currentRoute != Screen.SignUpScreen.route) {
                BottomBar(navController = navController)
            }
        }
    ) { padding ->

        NavHost(

            navController = navController,
            startDestination =  "splash",
            modifier = Modifier.padding(padding)

        ) {

            composable(Screen.MainScreen.route) {
                EventsScreen(navController = navController)
            }

            composable(Screen.MyBookingsScreen.route) {
                MyBookingsScreen(navController = navController)
            }

            composable(Screen.EventDetailsScreen.route) {
                EventDetailsScreen(navController = navController, event = sampleEvent())
            }

            composable(Screen.AccountScreen.route) {
                AccountScreen(navController = navController, currentUser = currentUser)
            }

            composable("splash") {
                SplashScreen(navController)
            }

            composable(Screen.LoginScreen.route) {
                val db = AppDatabase.getDatabase(LocalContext.current, CoroutineScope(Dispatchers.IO))
                LoginScreen(navController, db.userDao())
            }

            composable(Screen.SignUpScreen.route) {
                val db = AppDatabase.getDatabase(LocalContext.current, CoroutineScope(Dispatchers.IO))
                SignUpScreen(navController, db.userDao())
            }

        }
    }
}
