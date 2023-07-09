package com.kproject.quotes.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kproject.quotes.presentation.screens.auth.login.LoginScreen
import com.kproject.quotes.presentation.screens.auth.signup.SignUpScreen
import com.kproject.quotes.presentation.screens.components.quotes.SessionExpiredAlertDialog
import com.kproject.quotes.presentation.screens.home.HomeScreen
import com.kproject.quotes.presentation.screens.userprofile.UserProfileScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(isRefreshTokenExpired: Boolean) {
    val navController = rememberAnimatedNavController()

    var showSessionExpiredDialog by rememberSaveable { mutableStateOf(isRefreshTokenExpired) }
    LaunchedEffect(isRefreshTokenExpired) {
        if (isRefreshTokenExpired) {
            showSessionExpiredDialog = true
        }
    }

    AnimatedNavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(
                onNavigateToHomeScreen = {
                    navController.navigateAndClearBackStack(
                        navController = navController,
                        toRoute = Screen.HomeScreen.route,
                    )
                },
                onNavigateToSignUpScreen = {
                    navController.navigate(Screen.SignUpScreen.route)
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
                    navController.navigateAndClearBackStack(
                        navController = navController,
                        toRoute = Screen.HomeScreen.route,
                    )
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // HomeScreen
        composable(
            route = Screen.HomeScreen.route,
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
            HomeScreen(
                onNavigateToUserProfileScreen = {
                    navController.navigate(Screen.UserProfileScreen.route)
                },
                onNavigateToLoginScreen = {
                    navController.navigateAndClearBackStack(
                        navController = navController,
                        toRoute = Screen.LoginScreen.route,
                    )
                },
            )

            SessionExpiredAlertDialog(
                showDialog = showSessionExpiredDialog,
                onDismiss = { showSessionExpiredDialog = false },
                onNavigateToLoginScreen = {
                    navController.navigateAndClearBackStack(
                        navController = navController,
                        toRoute = Screen.LoginScreen.route,
                    )
                }
            )
        }

        // UserProfileScreen
        composable(
            route = Screen.UserProfileScreen.route,
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
            UserProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
            )

            SessionExpiredAlertDialog(
                showDialog = showSessionExpiredDialog,
                onDismiss = { showSessionExpiredDialog = false },
                onNavigateToLoginScreen = {
                    navController.navigateAndClearBackStack(
                        navController = navController,
                        toRoute = Screen.LoginScreen.route,
                    )
                }
            )
        }
    }
}

private fun NavHostController.navigateAndClearBackStack(
    navController: NavHostController,
    toRoute: String
) {
    navigate(toRoute) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
        launchSingleTop = true
    }
}