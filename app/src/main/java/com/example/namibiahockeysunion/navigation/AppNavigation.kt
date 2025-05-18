package com.example.namibiahockeysunion.navigation

import EventEntryScreen
import EventListScreen
import PlayerListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.namibiahockeysunion.ui.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("team_registration") }
            )
        }

        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { destination ->
                    when (destination) {
                        "home_player" -> navController.navigate("home_player") {
                            popUpTo("login") { inclusive = true }
                        }
                        "home_team" -> navController.navigate("home_team") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            )
        }

        composable("team_registration") {
            TeamRegistrationScreen(
                onBackClick = { navController.popBackStack() },
                navigateToLogin = { navController.navigate("login") }
            )
        }

        composable("home_team") {
            HomePageScreen(navController = navController)
        }

        composable("home_player") {
            PlayerPageScreen(navController)
        }

        composable("event_entry") {
            EventEntryScreen(navController = navController)
        }

        composable("player_registration") {
            PlayerRegistrationScreen(
                onBack = { navController.popBackStack() },
                onPlayerRegistered = { navController.navigate("home_team") }
            )
        }

        composable("player_list") {
            PlayerListScreen(navController = navController)
        }

        composable("event_list") {
            EventListScreen(navController = navController)
        }

        composable("announcement_post") {
            AnnouncementPostScreen(navController)
        }

        composable("announcement_feed") {
            AnnouncementFeedScreen(navController)
        }

        composable("reset_password") {
            ResetPasswordScreen(navController = navController)
        }
    }
}
