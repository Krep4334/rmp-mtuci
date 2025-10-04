package com.drivenext.app.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.drivenext.app.di.SimpleDI
import com.drivenext.app.presentation.navigation.DriveNextNavigation
import com.drivenext.app.presentation.screens.common.NoInternetScreen
import com.drivenext.app.presentation.theme.DriveNextTheme

/**
 * Главная активность приложения
 * Содержит навигацию и проверку подключения к интернету
 */
class MainActivity : ComponentActivity() {
    
    private val connectivityObserver by lazy { SimpleDI.provideNetworkConnectivityObserver() }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            DriveNextTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isConnected by connectivityObserver.isConnected.collectAsState()
                    
                    if (isConnected) {
                        DriveNextNavigation()
                    } else {
                        NoInternetScreen(
                            onRetryClick = {
                                connectivityObserver.checkConnection()
                            }
                        )
                    }
                }
            }
        }
    }
}
