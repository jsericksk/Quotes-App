package com.kproject.quotes.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kproject.quotes.presentation.screens.auth.login.LoginScreen
import com.kproject.quotes.presentation.screens.auth.signup.SignUpScreen
import com.kproject.quotes.presentation.screens.home.HomeScreen
import com.kproject.quotes.presentation.screens.userprofile.UserProfileScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(isUserLoggedIn: Boolean) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            if (isUserLoggedIn) {
                HomeScreen(
                    onNavigateToUserProfileScreen = {
                        navController.navigate(Screen.UserProfileScreen.route)
                    },
                    onNavigateToLoginScreen = {},
                )
            } else {
                LoginScreen(
                    onNavigateToHomeScreen = {
                        navController.navigateWithPopUp(
                            toRoute = Screen.HomeScreen.route,
                            fromRoute = Screen.LoginScreen.route
                        )
                    },
                    onNavigateToSignUpScreen = {
                        navController.navigate(Screen.SignUpScreen.route)
                    }
                )
            }
        }

        // HomeScreen
        composable(
            route = Screen.HomeScreen.route,
        ) {
            HomeScreen(
                onNavigateToUserProfileScreen = {
                    navController.navigate(Screen.UserProfileScreen.route)
                },
                onNavigateToLoginScreen = {},
            )
        }

        // UserProfileScreen
        composable(
            route = Screen.UserProfileScreen.route,
        ) {
            UserProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // SignUpScreen
        composable(
            route = Screen.SignUpScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ) {
            SignUpScreen(
                onNavigateToHomeScreen = {
                    navController.navigateWithPopUp(
                        toRoute = Screen.HomeScreen.route,
                        fromRoute = Screen.SignUpScreen.route
                    )
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

private fun NavHostController.navigateWithPopUp(toRoute: String, fromRoute: String) {
    navigate(toRoute) {
        popUpTo(fromRoute) {
            inclusive = true
        }
        launchSingleTop = true
    }
}