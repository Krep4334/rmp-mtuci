package com.drivenext.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drivenext.app.presentation.screens.auth.AuthWelcomeScreen
import com.drivenext.app.presentation.screens.auth.SignInScreen
import com.drivenext.app.presentation.screens.auth.SignUpScreen
import com.drivenext.app.presentation.screens.onboarding.OnboardingScreen

/**
 * Основная навигация приложения
 */
@Composable
fun DriveNextNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
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
                    // TODO: Navigate to main app screen
                }
            )
        }
        
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSignUpSuccess = {
                    // TODO: Navigate to main app screen
                }
            )
        }
    }
}
