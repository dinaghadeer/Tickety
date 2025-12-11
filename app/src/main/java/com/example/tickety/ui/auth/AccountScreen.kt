package com.example.tickety.ui.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tickety.navigation.Screen
import com.example.tickety.util.sha256
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import model.User
import model.UserDao

@Composable
fun AccountScreen(navController: NavController, currentUser: MutableState<User?>) {

    val user = currentUser.value   // get logged-in user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
           .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // profile Photo
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFF210D41), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user?.username?.firstOrNull()?.uppercase() ?: "A",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // username
        Text(
            text = user?.username ?: "Unknown User",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // email
        Text(
            text = user?.email ?: "",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Divider(color = Color.LightGray, thickness = 1.dp)

        Spacer(modifier = Modifier.height(32.dp))

        // logout button
        Button(
            onClick = {
                currentUser.value = null
                navController.navigate("LoginScreen") {
                    popUpTo(0)   // clear navigation history
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Logout", color = Color.White, fontSize = 18.sp)
        }
    }
}

