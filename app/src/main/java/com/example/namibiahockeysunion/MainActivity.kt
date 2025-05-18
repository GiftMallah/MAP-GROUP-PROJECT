package com.example.namibiahockeysunion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.namibiahockeysunion.navigation.AppNavigation
import com.example.namibiahockeysunion.ui.theme.NamibiaHockeysUnionTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            NamibiaHockeysUnionTheme {
                AppNavigation()
            }
        }
    }
}
