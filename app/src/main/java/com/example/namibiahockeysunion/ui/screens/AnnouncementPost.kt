package com.example.namibiahockeysunion.ui.screens

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.namibiahockeysunion.R
import com.example.namibiahockeysunion.data.model.Announcement
import com.google.firebase.database.FirebaseDatabase
import java.util.*

val timesNewRoman = FontFamily.Serif

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementPostScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val database = FirebaseDatabase.getInstance().getReference("announcements")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Namibia Hockey's Union",
                            fontSize = 26.sp,
                            fontFamily = timesNewRoman,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {

            // Background image
            Image(
                painter = painterResource(id = R.drawable.image3), // your image here
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Foreground content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp), // Increase this value as needed
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xDDFFFFFF)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                )
                {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Post Announcement", style = MaterialTheme.typography.headlineSmall)

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            label = { Text("Content") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (title.isNotBlank() && content.isNotBlank()) {
                                    val id = UUID.randomUUID().toString()
                                    val announcement = Announcement(id, title, content)
                                    database.child(id).setValue(announcement)
                                        .addOnSuccessListener {
                                            message = "Announcement posted!"
                                            title = ""
                                            content = ""

                                            navController.navigate("team_home")
                                        }
                                        .addOnFailureListener {
                                            message = "Failed to post."
                                        }
                                } else {
                                    message = "Please fill all fields."
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Post")
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    if (message.isNotEmpty()) {
                        Text(message, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
