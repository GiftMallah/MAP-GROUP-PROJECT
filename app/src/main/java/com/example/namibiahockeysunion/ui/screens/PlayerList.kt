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
import com.example.namibiahockeysunion.data.model.Player
import com.google.firebase.database.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerListScreen(navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().getReference("PlayerRegistration")
    var players by remember { mutableStateOf(listOf<Player>()) }

    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<Player>()
                val maleSnapshot = snapshot.child("Male")
                val femaleSnapshot = snapshot.child("Female")
                for (child in maleSnapshot.children) {
                    val player = child.getValue(Player::class.java)
                    player?.let { tempList.add(it) }
                }
                for (child in femaleSnapshot.children) {
                    val player = child.getValue(Player::class.java)
                    player?.let { tempList.add(it) }
                }
                players = tempList.reversed()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Namibia Hockey's Union",
                        color = Color(0xFF254C85) // Text color
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF254C85) // Back arrow color
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD9D9D9) // App bar background color
                )
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

            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Registered Players",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color(0xFF254C85)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn {
                            items(players) { player ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Name: ${player.name}")
                                        Text("Gender: ${player.gender}")
                                        Text("Position: ${player.position}")
                                        Text("Contact #: ${player.contactNumber}")
                                        Text("Email: ${player.email}")
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
