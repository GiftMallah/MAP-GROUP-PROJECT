import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.namibiahockeysunion.R
import com.example.namibiahockeysunion.data.model.Event
import com.google.firebase.database.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().getReference("events")
    var events by remember { mutableStateOf(listOf<Event>()) }

    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<Event>()
                for (child in snapshot.children) {
                    val event = child.getValue(Event::class.java)
                    event?.let { tempList.add(it) }
                }
                events = tempList.reversed()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Namibia Hockey's Union", color = Color(0xFF254C85))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF254C85))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFD9D9D9))
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.image3),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Event List",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color(0xFF254C85)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn {
                            items(events) { event ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Event Name: ${event.name}")
                                        Text("Date: ${event.dateTime.take(10)}")
                                        Text("Time: ${event.dateTime.takeLast(5)}")
                                        Text("League Type: ${event.leagueType}")
                                        Text("Gender: ${event.gender}")
                                        Text("Age Group: ${event.ageGroup}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
