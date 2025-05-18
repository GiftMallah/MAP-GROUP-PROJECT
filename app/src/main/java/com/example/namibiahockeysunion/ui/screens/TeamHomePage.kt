package com.example.namibiahockeysunion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.namibiahockeysunion.R
import com.example.namibiahockeysunion.data.model.Event
import com.example.namibiahockeysunion.data.model.Player
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageScreen(navController: NavController) {
    val backgroundPainter: Painter = painterResource(id = R.drawable.image)
    val eventDatabase = FirebaseDatabase.getInstance().getReference("events")
    val playerDatabase = FirebaseDatabase.getInstance().getReference("PlayerRegistration")

    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var players by remember { mutableStateOf<List<Player>>(emptyList()) }

    LaunchedEffect(Unit) {
        eventDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val eventList = mutableListOf<Event>()
                for (child in snapshot.children) {
                    val event = child.getValue(Event::class.java)
                    event?.let { eventList.add(it) }
                }
                events = eventList.reversed()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    LaunchedEffect(Unit) {
        playerDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playerList = mutableListOf<Player>()
                for (genderSnapshot in snapshot.children) {
                    for (playerSnapshot in genderSnapshot.children) {
                        val player = playerSnapshot.getValue(Player::class.java)
                        player?.let { playerList.add(it) }
                    }
                }
                players = playerList.reversed()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Namibia Hockey's Union",
                            fontSize = 22.sp,
                            color = Color(0xFF254C85),
                        )
                    }
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color(0xFF254C85),
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD9D9D9)
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF1A231C),
                modifier = Modifier.height(78.dp)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("announcement_feed") },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Announcement Feed") },
                    label = { Text("Announcement", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("announcement_post") },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Post") },
                    label = { Text("Post", fontSize = 10.sp) }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Image(
                painter = backgroundPainter,
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Team Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF)),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Head Coach: John Smith", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Team Manager: Sarah Johnson", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Contact Information", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {
                            Icon(Icons.Default.Phone, contentDescription = "Phone", tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("+264 81 234 5678", fontSize = 16.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Email, contentDescription = "Email", tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("coach@namhockey.na", fontSize = 16.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Events", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextButton(onClick = { navController.navigate("event_entry") }, colors = ButtonDefaults.textButtonColors(contentColor = Color.White)) {
                            Text("Add Events")
                        }
                        TextButton(
                            onClick = { navController.navigate("event_list") },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                        ) {
                            Text("View All")
                        }

                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    events.take(2).forEach { event ->
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF)),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(event.name, fontSize = 14.sp)
                                Text(event.dateTime.take(10), fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                                Text(event.dateTime.takeLast(5), fontSize = 14.sp, color = Color.Gray)
                                Text(event.leagueType, fontSize = 14.sp)
                                Text(event.gender, fontSize = 14.sp)
                                Text(event.ageGroup, fontSize = 14.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Players Section
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Players", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextButton(onClick = { navController.navigate("player_registration") },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                        ) {
                            Text("Add Players")
                        }
                        TextButton(
                            onClick = { navController.navigate("player_list") },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                        ) {
                            Text("View Players")
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF)),
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        players.forEach { player ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = player.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.weight(1f)
                                )
                                TextButton(
                                    onClick = { /* Handle View Profile */ },
                                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF44825D))
                                ) {
                                    Text("View Profile", fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
