package com.kproject.quotes.presentation.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("signup_screen")
    object HomeScreen : Screen("home_screen")
    object UserProfileScreen : Screen("user_profile_screen")
}