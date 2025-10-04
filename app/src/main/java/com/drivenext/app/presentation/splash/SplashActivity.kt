package com.drivenext.app.presentation.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.drivenext.app.presentation.main.MainActivity
import com.drivenext.app.presentation.screens.splash.SplashScreenContent
import com.drivenext.app.presentation.theme.DriveNextTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Экран загрузки приложения
 * Отображает логотип, название и слоган на 2-3 секунды
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    
    private lateinit var viewModel: SplashViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewModel = SplashViewModel()
        
        // Устанавливаем Compose контент
        setContent {
            DriveNextTheme {
                SplashScreenContent()
            }
        }
        
        // Запуск логики загрузки
        lifecycleScope.launch {
            viewModel.startLoading()
            
            // Минимальная задержка для показа splash screen
            delay(2000)
            
            // Переход к главному экрану
            navigateToMainActivity()
            finish()
        }
    }
    
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        // Используем современный API для анимации переходов
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, android.R.anim.fade_in, android.R.anim.fade_out)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}
