package com.example.clothinginventoryapp.ui

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object ViewScreen : Screen("view_screen")
    object AddScreen : Screen("add_screen")
    object RemoveScreen : Screen("remove_screen")
}
