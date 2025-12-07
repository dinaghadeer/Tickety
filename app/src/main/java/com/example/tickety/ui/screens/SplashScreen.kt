package com.example.tickety.ui.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tickety.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    // delay then navigate
    LaunchedEffect(Unit) {
        delay(4000)  // 2 seconds
        navController.navigate("EventsScreen") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF210D41)), // Purple color
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "App Logo",
            modifier = Modifier.size(600.dp)
        )
    }
}