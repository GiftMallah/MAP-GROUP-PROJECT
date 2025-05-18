package com.example.namibiahockeysunion.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import com.example.namibiahockeysunion.data.model.Event
import java.util.*

@Composable
fun EventEntryScreen() {
    var eventName by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val database = FirebaseDatabase.getInstance().getReference("events")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Event Entry", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = eventName,
            onValueChange = { eventName = it },
            label = { Text("Event Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = eventDate,
            onValueChange = { eventDate = it },
            label = { Text("Event Date (e.g. 2025-04-20)") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (eventName.isNotBlank() && eventDate.isNotBlank()) {
                val eventId = UUID.randomUUID().toString()
                val event = Event(eventId, eventName, eventDate)
                database.child(eventId).setValue(event)
                    .addOnSuccessListener {
                        message = "Event created successfully!"
                        eventName = ""
                        eventDate = ""
                    }
                    .addOnFailureListener {
                        message = "Failed to create event."
                    }
            } else {
                message = "Please fill all fields."
            }
        }) {
            Text("Create Event")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (message.isNotEmpty()) {
            Text(message, color = MaterialTheme.colorScheme.primary)
        }
    }
}
