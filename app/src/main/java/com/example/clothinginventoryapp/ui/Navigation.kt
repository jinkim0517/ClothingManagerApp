package com.example.clothinginventoryapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clothinginventoryapp.persistence.ClothingEvent

@Composable
fun Navigation(state: ClothingState, event: (ClothingEvent) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, state = state, onEvent = event)
        }

        composable(route = Screen.ViewScreen.route) {
            ViewScreen(navController = navController, state = state, onEvent = event)
        }

        composable(route = Screen.AddScreen.route) {
            AddScreen(navController = navController, state = state, onEvent = event)
        }

        composable(route = Screen.RemoveScreen.route) {
            RemoveScreen(navController = navController, state = state, onEvent = event)
        }
    }
}