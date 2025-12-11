package com.example.tickety.navigation
import com.example.tickety.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {

    object SplashScreen : Screen("splash","splash", Icons.Filled.Home)

    object LoginScreen : Screen("LoginScreen", "Login", Icons.Filled.Home)

    object SignUpScreen : Screen("SignUpScreen", "Sign Up", Icons.Filled.Home)

    object MainScreen : Screen("EventsScreen", "Home", Icons.Filled.Home)
    object EventDetailsScreen : Screen("EventDetailsScreen/{eventId}", "Event Details",  Icons.Filled.Home){
        fun createRoute(eventId: Int) = "EventDetailsScreen/$eventId"
    }
    object MyBookingsScreen : Screen("MyBookingsScreen", "My Tickets", Icons.Filled.ConfirmationNumber)

    object AccountScreen : Screen("AccountScreen", "Account",  Icons.Filled.Person)
}