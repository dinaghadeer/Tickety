package com.example.tickety.ui.auth

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import model.UserDao
import model.saveCurrentUserId


@Composable
fun LoginScreen(navController: NavController, userDao: UserDao, currentUser: MutableState<model.User?>) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()  // it's like a async thread instead of working with database in the main thread, it would freeze and slow the app

    val context = LocalContext.current

    Box(   // for the entire screen , makes the screen fill the device
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF210D41), Color(0xFFF3E8FF)),
                    start = Offset(0f, 0f),
                    end = Offset.Infinite
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Text("Welcome Back!",
                modifier = Modifier.align(Alignment.Start),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(56.dp))

            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.15f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                disabledContainerColor = Color.White.copy(alpha = 0.1f),

                cursorColor = Color.White,

                focusedBorderColor = Color(0xFF210D41),
                unfocusedBorderColor = Color(0xFF210D41),

                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),

                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )

            //email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (error.isNotEmpty()) {
                Text(error, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                onClick = {
                    if (navController != null && userDao != null) {
                        scope.launch {  // after checking with the database return to the main thread
                            try {
                                val user = withContext(Dispatchers.IO) {  // in backstage do some database work but don't freez the ui
                                    userDao.login(email, sha256(password))
                                }
                                if (user != null) {
                                    currentUser.value = user
                                    navController.navigate(Screen.MainScreen.route) {
                                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                                    }
                                    val loggedInUser = userDao.getUserByEmail(email)
                                    if (loggedInUser != null) {
                                        saveCurrentUserId(context, loggedInUser.id)
                                    }
                                } else {
                                    error = "Wrong email or password"
                                }
                            } catch (e: Exception) {
                                error = "Login failed: ${e.message}"
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .fillMaxWidth()
                    .height(48.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF210D41)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Login", color = Color.White, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))


            Button(
                onClick = { navController.navigate(Screen.SignUpScreen.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF210D41)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Create Account", color = Color.White, fontSize = 18.sp)
            }

        }
    }

}
