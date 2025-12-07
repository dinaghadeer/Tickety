package com.example.tickety.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.tickety.ui.screens.SplashScreen
import com.example.tickety.ui.screens.details.EventDetailsScreen
import com.example.tickety.ui.screens.details.sampleEvent
import com.example.tickety.ui.screens.events.EventsScreen
import com.example.tickety.ui.screens.mybookings.MyBookingsScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // Observe current route
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.SplashScreen.route) {
                BottomBar(navController = navController)
            }
        }
    ) { padding ->

        NavHost(

            navController = navController,
            startDestination =  "splash",          // Screen.MainScreen.route,
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

//            composable(NavItems.Account.route) {
//                AccountScreen(navController = navController)
//            }

            composable("splash") {
                SplashScreen(navController)
            }
        }
    }
}
