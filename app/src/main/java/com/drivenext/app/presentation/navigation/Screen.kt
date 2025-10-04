package com.drivenext.app.presentation.navigation

/**
 * Sealed class для определения экранов навигации
 */
sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object AuthWelcome : Screen("auth_welcome")
    object SignIn : Screen("sign_in")
    object SignUp : Screen("sign_up")
    object SignUpStep2 : Screen("sign_up_step_2")
    object SignUpStep3 : Screen("sign_up_step_3")
    object SignUpSuccess : Screen("sign_up_success")
    object Home : Screen("home")
}
