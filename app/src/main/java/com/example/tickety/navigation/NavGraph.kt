package com.example.tickety.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.platform.LocalContext
import model.AppDatabase
import com.example.tickety.ui.auth.LoginScreen
import com.example.tickety.ui.auth.SignUpScreen
import com.example.tickety.ui.screens.events.EventsScreen
import com.example.tickety.ui.screens.details.EventDetailsScreen
import com.example.tickety.ui.screens.mybookings.MyBookingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// تأكدي من تعريف الـ Routes في ملف Screen.kt كالتالي:
// sealed class Screen(val route: String) {
//     object LoginScreen : Screen("login_screen")
//     object SignUpScreen : Screen("signup_screen")
//     object MainScreen : Screen("home_screen") // دي صفحة الـ Events
//     object MyBookingsScreen : Screen("my_bookings")
//     object EventDetailsScreen : Screen("event_details/{eventId}") {
//         fun createRoute(eventId: Int) = "event_details/$eventId"
//     }
// }

@Composable
fun AppNavigation(userId: Long) { // الـ userId هنا ممكن نشيله لو مش محتاجينه في أول تشغيلة، لكن سيبيه مش هيضر
    val navController = rememberNavController()
    val context = LocalContext.current

    // Database instance for Login/Signup
    val db = AppDatabase.getDatabase(context, CoroutineScope(Dispatchers.IO))

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {

        // Login Screen
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, userDao = db.userDao())
        }

        // SignUp Screen
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController, userDao = db.userDao())
        }

        // Events Screen (Main Home)
        composable(route = Screen.MainScreen.route) {
            EventsScreen(navController = navController)
        }

        // Event Details Screen
        composable(
            route = Screen.EventDetailsScreen.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getInt("eventId") ?: 0

            // ✅ التعديل هنا: شلنا userId من الأقواس
            EventDetailsScreen(navController = navController, eventId = eventId)
        }

        // My Bookings Screen
        composable(route = Screen.MyBookingsScreen.route) {

            // ✅ التعديل هنا: شلنا userId من الأقواس
            MyBookingsScreen(navController = navController)
        }
    }
}