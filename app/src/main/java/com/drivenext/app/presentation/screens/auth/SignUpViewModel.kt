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
    
    private val signUpUseCase = SimpleDI.signUpUseCase
    
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    
    fun signUp(email: String, password: String, confirmPassword: String, isTermsAccepted: Boolean) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                emailError = null,
                passwordError = null,
                confirmPasswordError = null,
                errorMessage = null
            )
            
            // Дополнительная валидация
            if (!isTermsAccepted) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Необходимо принять условия использования"
                )
                return@launch
            }
            
            when (val result = signUpUseCase(email, password, confirmPassword)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSignUpSuccessful = true,
                        userId = result.data
                    )
                }
                is Resource.Error -> {
                    handleError(result.message ?: "Неизвестная ошибка")
                }
                is Resource.Loading -> {
                    // Состояние загрузки уже установлено
                }
            }
        }
    }
    
    private fun handleError(errorMessage: String) {
        when {
            errorMessage.contains("email", ignoreCase = true) -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    emailError = errorMessage
                )
            }
            errorMessage.contains("пароль", ignoreCase = true) && 
            errorMessage.contains("совпадают", ignoreCase = true) -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    confirmPasswordError = errorMessage
                )
            }
            errorMessage.contains("пароль", ignoreCase = true) -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    passwordError = errorMessage
                )
            }
            else -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = errorMessage
                )
            }
        }
    }
}

data class SignUpUiState(
    val isLoading: Boolean = false,
    val isSignUpSuccessful: Boolean = false,
    val userId: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val errorMessage: String? = null
)
