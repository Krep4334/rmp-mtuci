package com.drivenext.app.presentation.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.drivenext.app.data.local.UserPreferences
import com.drivenext.app.presentation.screens.auth.AuthWelcomeScreen
import com.drivenext.app.presentation.screens.auth.SignInScreen
import com.drivenext.app.presentation.screens.auth.SignUpScreen
import com.drivenext.app.presentation.screens.auth.SignUpStep2Screen
import com.drivenext.app.presentation.screens.auth.SignUpStep3Screen
import com.drivenext.app.presentation.screens.auth.SignUpSuccessScreen
import com.drivenext.app.presentation.screens.main.MainScreen
import com.drivenext.app.presentation.screens.onboarding.OnboardingScreen

/**
 * Основная навигация приложения
 */
@Composable
fun DriveNextNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    
    // Проверяем, авторизован ли пользователь для определения стартового экрана
    val isLoggedIn by remember { mutableStateOf(userPreferences.isLoggedIn()) }
    println("DEBUG: Проверка авторизации при запуске - isLoggedIn: $isLoggedIn")
    userPreferences.debugShowAllData()
    
    // Определяем стартовый экран в зависимости от статуса авторизации
    val startDestination = if (isLoggedIn) {
        Screen.Home.route
    } else {
        Screen.Onboarding.route
    }
    println("DEBUG: Стартовый экран: $startDestination")
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onSkip = {
                    navController.navigate(Screen.AuthWelcome.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onFinish = {
                    navController.navigate(Screen.AuthWelcome.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.AuthWelcome.route) {
            AuthWelcomeScreen(
                onSignInClick = {
                    navController.navigate(Screen.SignIn.route)
                },
                onSignUpClick = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        
        composable(Screen.SignIn.route) {
            SignInScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSignInSuccess = {
                    println("DEBUG: Успешный вход, переход на главный экран")
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.AuthWelcome.route) { inclusive = false }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToStep2 = {
                    navController.navigate(Screen.SignUpStep2.route)
                }
            )
        }
        
        composable(Screen.SignUpStep2.route) {
            SignUpStep2Screen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToStep3 = {
                    navController.navigate(Screen.SignUpStep3.route)
                }
            )
        }
        
        composable(Screen.SignUpStep3.route) {
            SignUpStep3Screen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegistrationSuccess = {
                    navController.navigate(Screen.SignUpSuccess.route) {
                        popUpTo(Screen.AuthWelcome.route) { inclusive = false }
                    }
                }
            )
        }
        
        composable(Screen.SignUpSuccess.route) {
            SignUpSuccessScreen(
                onContinue = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUpSuccess.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Home.route) {
            MainScreen(
                onLogout = {
                    userPreferences.logout()
                    navController.navigate(Screen.AuthWelcome.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
