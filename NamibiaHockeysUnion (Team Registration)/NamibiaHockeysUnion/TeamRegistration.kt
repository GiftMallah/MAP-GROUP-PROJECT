package com.example.namibiahockeysunion.ui.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.namibiahockeysunion.R
import com.example.namibiahockeysunion.data.model.Team
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamRegistrationScreen(
    onBackClick: () -> Unit = {},
    navigateToLogin: () -> Unit
) {
    var teamName by remember { mutableStateOf("") }
    var teamCategory by remember { mutableStateOf("") }
    var genderDivision by remember { mutableStateOf("") }
    var tournamentName by remember { mutableStateOf("") }
    var divisionLevel by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val ageGroups = listOf("U14", "U16", "U18", "Adult")
    val genders = listOf("Male", "Female")
    var expandedAge by remember { mutableStateOf(false) }
    var expandedGender by remember { mutableStateOf(false) }

    val database = FirebaseDatabase.getInstance().getReference("teams")

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
                    Box(modifier = Modifier.padding(top = 8.dp)) {
                        TextButton(onClick = onBackClick) {
                            Text("Back", color = Color(0xFF254C85))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD9D9D9),
                    titleContentColor = Color(0xFF254C85)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.image), // Replace with your image resource
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Content container with color
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Team Registration", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Coach Details", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            placeholder = { Text("First Name") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                        )
                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            placeholder = { Text("Last Name") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

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


                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Team Details", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = teamName,
                        onValueChange = { teamName = it },
                        placeholder = { Text("Team Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expandedAge,
                            onExpandedChange = { expandedAge = !expandedAge },
                            modifier = Modifier.weight(1f)
                        ) {
                            TextField(
                                value = teamCategory,
                                onValueChange = { },
                                placeholder = { Text("Age Group") },
                                readOnly = true,
                                singleLine = true,
                                modifier = Modifier.menuAnchor(),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAge)
                                },
                            )
                            ExposedDropdownMenu(
                                expanded = expandedAge,
                                onDismissRequest = { expandedAge = false }
                            ) {
                                ageGroups.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            teamCategory = option
                                            expandedAge = false
                                        }
                                    )
                                }
                            }
                        }

                        ExposedDropdownMenuBox(
                            expanded = expandedGender,
                            onExpandedChange = { expandedGender = !expandedGender },
                            modifier = Modifier.weight(1f)
                        ) {
                            TextField(
                                value = genderDivision,
                                onValueChange = { },
                                placeholder = { Text("Gender") },
                                readOnly = true,
                                singleLine = true,
                                modifier = Modifier.menuAnchor(),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGender)
                                },
                            )
                            ExposedDropdownMenu(
                                expanded = expandedGender,
                                onDismissRequest = { expandedGender = false }
                            ) {
                                genders.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            genderDivision = option
                                            expandedGender = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = tournamentName,
                        onValueChange = { tournamentName = it },
                        placeholder = { Text("League/Tournament") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = divisionLevel,
                        onValueChange = { divisionLevel = it },
                        placeholder = { Text("Division Level") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        placeholder = { Text("Team Address") },
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(onClick = {
                        if (teamName.isNotBlank() && firstName.isNotBlank() && contactNumber.isNotBlank()) {
                            val teamId = UUID.randomUUID().toString()
                            val team = Team(
                                teamId = teamId,
                                teamName = teamName,
                                ageGroup = teamCategory,
                                genderDivision = genderDivision,
                                tournamentName = tournamentName,
                                divisionLevel = divisionLevel,
                                firstName = firstName,
                                lastName = lastName,
                                contactNumber = contactNumber,
                                email = email,
                                password = password,
                                address = address
                            )
                            database.child(teamId).setValue(team)
                                .addOnSuccessListener {
                                    message = "Team registered successfully!"
                                    teamName = ""; teamCategory = ""; genderDivision = ""
                                    tournamentName = ""; divisionLevel = ""
                                    firstName = ""; lastName = ""; contactNumber = ""
                                    email = ""; password = ""; address = ""
                                    navigateToLogin()
                                }
                                .addOnFailureListener {
                                    message = "Failed to register team."
                                }
                        } else {
                            message = "Please fill required fields."
                        }
                    }) {
                        Text("Register Team")
                    }

                    if (message.isNotEmpty()) {
                        Text(
                            message,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
