package com.drivenext.app.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drivenext.app.di.SimpleDI
import com.drivenext.app.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана регистрации (первый шаг)
 */
class SignUpViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    
    fun validateStep1(email: String, password: String, confirmPassword: String, isTermsAccepted: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                emailError = null,
                passwordError = null,
                confirmPasswordError = null,
                errorMessage = null
            )
            
            // Валидация email
            if (email.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    emailError = "Введите корректный адрес электронной почты."
                )
                return@launch
            }
            
            if (!isValidEmail(email)) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    emailError = "Введите корректный адрес электронной почты."
                )
                return@launch
            }
            
            // Валидация пароля
            if (password.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    passwordError = "Пароль не может быть пустым"
                )
                return@launch
            }
            
            if (password.length < 8) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    passwordError = "Пароль должен содержать минимум 8 символов"
                )
                return@launch
            }
            
            // Валидация подтверждения пароля
            if (confirmPassword.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    confirmPasswordError = "Подтвердите пароль"
                )
                return@launch
            }
            
            if (password != confirmPassword) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    confirmPasswordError = "Пароли не совпадают."
                )
                return@launch
            }
            
            // Валидация чекбокса
            if (!isTermsAccepted) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Необходимо согласиться с условиями обслуживания и политикой конфиденциальности."
                )
                return@launch
            }
            
            // Если все валидации прошли успешно
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isStep1Valid = true
            )
        }
    }
    
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return emailPattern.matches(email)
    }
}

data class SignUpUiState(
    val isLoading: Boolean = false,
    val isStep1Valid: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val errorMessage: String? = null
)
