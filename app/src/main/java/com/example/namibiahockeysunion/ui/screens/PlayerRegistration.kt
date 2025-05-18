package com.example.namibiahockeysunion.ui.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.namibiahockeysunion.R
import com.example.namibiahockeysunion.data.model.Player
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerRegistrationScreen(
    onBack: () -> Unit = {},
    onPlayerRegistered: () -> Unit // New callback to navigate after successful registration
) {
    var gender by remember { mutableStateOf("Male") }
    var email by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text(
                            "Namibia Hockey's Union",
                            fontSize = 26.sp,
                            color = Color(0xFF254C85)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF254C85)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD9D9D9),
                    titleContentColor = Color(0xFF254C85)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.image1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF))
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Player Registration", style = MaterialTheme.typography.headlineSmall)

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = { gender = "Male" }) {
                                Text(
                                    "Male",
                                    fontSize = 20.sp,
                                    color = if (gender == "Male") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            }
                            TextButton(onClick = { gender = "Female" }) {
                                Text(
                                    "Female",
                                    fontSize = 20.sp,
                                    color = if (gender == "Female") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                if (it.length <= 50) { // Optional length limit
                                    email = it
                                }
                            },
                            label = { Text("Email") },
                            isError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches(),
                            modifier = Modifier.fillMaxWidth(),
                            supportingText = {
                                if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                    Text("Invalid email format", color = Color.Red)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = firstName,
                                onValueChange = { firstName = it },
                                label = { Text("First Name") },
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = lastName,
                                onValueChange = { lastName = it },
                                label = { Text("Last Name") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = contactNumber,
                            onValueChange = {
                                if (it.matches(Regex("^\\+?[0-9]*$"))) {
                                    contactNumber = it
                                }
                            },
                            label = { Text("Contact Number") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = position,
                            onValueChange = { position = it },
                            label = { Text("Position (e.g. Defender)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (
                                    email.isNotBlank() && firstName.isNotBlank() && lastName.isNotBlank() &&
                                    position.isNotBlank() && contactNumber.isNotBlank() &&
                                    password.isNotBlank()
                                ) {
                                    val playerRef = FirebaseDatabase.getInstance()
                                        .getReference("PlayerRegistration")
                                        .child(gender)
                                        .push()

                                    val autoGeneratedId = playerRef.key ?: ""

                                    val player = Player(
                                        playerId = autoGeneratedId,
                                        name = "$firstName $lastName",
                                        teamId = "Team001", // Replace with actual logic if needed
                                        firstName = firstName,
                                        lastName = lastName,
                                        gender = gender,
                                        email = email,
                                        contactNumber = contactNumber,
                                        position = position,
                                        password = password
                                    )

                                    playerRef.setValue(player)
                                        .addOnSuccessListener {
                                            message = "Player registered successfully!"
                                            email = ""
                                            firstName = ""
                                            lastName = ""
                                            position = ""
                                            contactNumber = ""
                                            password = ""

                                            onPlayerRegistered() // 👈 Navigate to home_team after registration
                                        }
                                        .addOnFailureListener {
                                            message = "Failed to register player."
                                        }
                                } else {
                                    message = "Please fill all fields."
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF44825D),
                                contentColor = Color.White
                            ),
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Register Player", fontSize = 18.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (message.isNotEmpty()) {
                            Text(message, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
