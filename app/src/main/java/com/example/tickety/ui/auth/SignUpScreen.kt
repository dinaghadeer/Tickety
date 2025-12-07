package com.example.tickety.ui.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.tickety.navigation.Screen
import com.example.tickety.util.sha256
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen(navController: NavController, userDao: UserDao) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()  // it's like a async thread instead of working with database in the main thread, it would freeze and slow the app

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
        // transparent triangle
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, size.height * 0.2f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height * 0.4f)
                close()
            }
            drawPath(path, color = Color.White.copy(alpha = 0.15f))
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                "Let's Create\n\nAccount!",
                modifier = Modifier.align(Alignment.Start),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,

            )

            Spacer(modifier = Modifier.height(56.dp))

            // Input fields

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


            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Create Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )


            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
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

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    error = ""
                    if (password != confirmPassword) {
                        error = "Passwords do not match"
                        return@Button  // do not continue the signup process - just exit the lambda "onclick"
                    }

                    if (navController != null && userDao != null) {
                        if (username.isBlank() || email.isBlank() || password.isBlank()) {
                            error = "All fields must be filled."
                        } else {
                            // database check
                            scope.launch {  // after checking with the database return to the main thread
                                val existing = withContext(Dispatchers.IO) {
                                    userDao.getUserByEmail(email)
                                }

                                if (existing != null) {
                                    error = "Email already exists."
                                } else {

                                    try {
                                        withContext(Dispatchers.IO) {
                                            val newUser = User(
                                                username = username,
                                                email = email,
                                                password = sha256(password)
                                            )
                                            userDao.insertUser(newUser)
                                        }
                                        navController.navigate(Screen.MainScreen.route)
                                    } catch (e: Exception) {
                                        error = "Signup failed: ${e.message}"
                                    }
                                }
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
                Text("Sign Up", color = Color.White, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)

                },
                modifier = Modifier.fillMaxWidth()
                    .fillMaxWidth()
                    .height(48.dp)
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF210D41)),
                shape = RoundedCornerShape(16.dp)
            ){
                Text("Login", color = Color.White, fontSize = 18.sp)
            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignupScreenPreview() {
//    SignupScreen()
//}
