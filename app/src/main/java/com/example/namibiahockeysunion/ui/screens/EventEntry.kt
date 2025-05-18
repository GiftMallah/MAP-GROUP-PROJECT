import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.namibiahockeysunion.R
import com.example.namibiahockeysunion.data.model.Event
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEntryScreen(navController: NavController) {
    val context = LocalContext.current

    var eventName by remember { mutableStateOf("") }
    var eventDateTime by remember { mutableStateOf("") }
    var leagueType by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var ageGroup by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    fun openDateTimePicker() {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hour)
                        calendar.set(Calendar.MINUTE, minute)

                        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                        eventDateTime = format.format(calendar.time)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    val database = FirebaseDatabase.getInstance().getReference("events")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Namibia Hockey's Union",
                            color = Color(0xFF254C85),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF254C85)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD9D9D9)
                )
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xDDFFFFFF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Event Entry",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF254C85),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        OutlinedTextField(
                            value = eventName,
                            onValueChange = { eventName = it },
                            placeholder = { Text("Event Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { openDateTimePicker() }
                        ) {
                            OutlinedTextField(
                                value = eventDateTime,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Event Date & Time") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        OutlinedTextField(
                            value = leagueType,
                            onValueChange = { leagueType = it },
                            placeholder = { Text("Type of League") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = gender,
                            onValueChange = { gender = it },
                            placeholder = { Text("Gender") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = ageGroup,
                            onValueChange = { ageGroup = it },
                            placeholder = { Text("Age Group") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (eventName.isNotBlank() && eventDateTime.isNotBlank()
                                    && leagueType.isNotBlank() && gender.isNotBlank() && ageGroup.isNotBlank()
                                ) {
                                    val eventId = UUID.randomUUID().toString()
                                    val event = Event(
                                        id = eventId,
                                        name = eventName,
                                        dateTime = eventDateTime,
                                        leagueType = leagueType,
                                        gender = gender,
                                        ageGroup = ageGroup
                                    )

                                    database.child(eventId).setValue(event)
                                        .addOnSuccessListener {
                                            message = "Event created successfully!"
                                            // Navigate back to home_team screen
                                            navController.navigate("home_team") {
                                                popUpTo("event_entry") { inclusive = true }
                                            }
                                        }
                                        .addOnFailureListener {
                                            message = "Failed to create event."
                                        }
                                } else {
                                    message = "Please fill all fields."
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF44825D)
                            )
                        ) {
                            Text("Create Event", color = Color.White)
                        }

                        if (message.isNotEmpty()) {
                            Text(message, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
