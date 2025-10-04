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
 * ViewModel для экрана входа в систему
 */
class SignInViewModel : ViewModel() {
    
    private val signInUseCase = SimpleDI.signInUseCase
    
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()
    
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                emailError = null,
                passwordError = null,
                errorMessage = null
            )
            
            when (val result = signInUseCase(email, password)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSignInSuccessful = true,
                        user = result.data
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

data class SignInUiState(
    val isLoading: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val user: com.drivenext.app.domain.model.User? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val errorMessage: String? = null
)
