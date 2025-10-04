package com.drivenext.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drivenext.app.di.SimpleDI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана загрузки
 * Проверяет состояние аутентификации и подготавливает приложение к работе
 */
class SplashViewModel : ViewModel() {
    
    private val authRepository = SimpleDI.provideAuthRepository()
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _isUserAuthenticated = MutableStateFlow<Boolean?>(null)
    val isUserAuthenticated: StateFlow<Boolean?> = _isUserAuthenticated
    
    fun startLoading() {
        viewModelScope.launch {
            try {
                // Проверяем, есть ли аутентифицированный пользователь
                val currentUser = authRepository.getCurrentUser()
                _isUserAuthenticated.value = currentUser.data != null
                
            } catch (e: Exception) {
                _isUserAuthenticated.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}
