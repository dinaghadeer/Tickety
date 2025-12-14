package com.example.tickety.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.tickety.ui.screens.SplashScreen
import com.example.tickety.ui.screens.details.EventDetailsScreen
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

    // observe current route
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    val currentUser = remember { mutableStateOf<User?>(null) }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.SplashScreen.route &&     // if the screen is splash or login or signup don't show the bottom bar
                currentRoute != Screen.LoginScreen.route &&
                currentRoute != Screen.SignUpScreen.route
            ) {
                BottomBar(navController = navController, currentUser = currentUser) // pass userId
            }
        }
    ) { padding ->

        NavHost(  // have all screens and their routes

            navController = navController,
            startDestination =  "splash",
            modifier = Modifier.padding(padding)

        ) {

            composable(Screen.MainScreen.route) {
                EventsScreen(navController = navController)
            }

            composable(
                route = "MyBookingsScreen/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.IntType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                MyBookingsScreen(navController = navController, currentUser = currentUser)
            }

            composable(Screen.EventDetailsScreen.route, arguments = listOf(navArgument("eventId") { type = NavType.IntType })) {
                    backStackEntry ->
                val eventId = backStackEntry.arguments?.getInt("eventId") ?: return@composable
                val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
                EventDetailsScreen(navController = navController, eventId = eventId, currentUser = currentUser)
            }

            composable(Screen.AccountScreen.route) {
                AccountScreen(navController = navController, currentUser = currentUser)
            }

            composable("splash") {
                SplashScreen(navController)
            }

            composable(Screen.LoginScreen.route) {
                val db = AppDatabase.getDatabase(LocalContext.current, CoroutineScope(Dispatchers.IO))
                LoginScreen(navController, db.userDao(), currentUser = currentUser)
            }

            composable(Screen.SignUpScreen.route) {
                val db = AppDatabase.getDatabase(LocalContext.current, CoroutineScope(Dispatchers.IO))
                SignUpScreen(navController, db.userDao(), currentUser = currentUser)
            }

        }
    }
}
