package com.example.tickety.navigation


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.shape.RoundedCornerShape


@Composable
fun BottomBar(navController: NavController, userId: Long) {

    val items = listOf(   // list of the bottom bar screens
        Screen.MainScreen,
        Screen.MyBookingsScreen,
        Screen.AccountScreen
    )

    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // top edges rounded
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route   // trying to know what page i am in right now

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    // if MyBookingsScreen add userId
                    val route = if (screen == Screen.MyBookingsScreen) {
                        "MyBookingsScreen/$userId"
                    } else {
                        screen.route   // keep the route without passing the userid
                    }

                    navController.navigate(route) {  // navigate to the screen
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(screen.title)
                }
            )
        }
    }
}
