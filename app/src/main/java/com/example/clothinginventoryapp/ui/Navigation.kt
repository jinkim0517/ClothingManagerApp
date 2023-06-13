package com.example.clothinginventoryapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }

        composable(route = Screen.ViewScreen.route) {
            ViewScreen(navController = navController)
        }

        composable(route = Screen.AddScreen.route) {
            AddScreen(navController = navController)
        }

        composable(route = Screen.RemoveScreen.route) {
            RemoveScreen(navController = navController)
        }
    }
}