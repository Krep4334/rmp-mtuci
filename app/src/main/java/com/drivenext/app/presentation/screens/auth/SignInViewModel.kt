package com.drivenext.app.presentation.screens.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drivenext.app.data.local.UserPreferences
import com.drivenext.app.di.SimpleDI
import com.drivenext.app.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана входа в систему
 */
class SignInViewModel(
    private val context: Context
) : ViewModel() {
    
    private val userPreferences = UserPreferences(context)
    private val signInUseCase = SimpleDI.signInUseCase
    
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()
    
    // Выполняет вход пользователя, проверяя данные в локальном хранилище
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                emailError = null,
                passwordError = null,
                errorMessage = null
            )
            
            // Проверяем сохраненные данные пользователя
            userPreferences.debugShowAllData()
            val userData = userPreferences.getUserData()
            println("DEBUG: Попытка входа - email: $email, пароль: $password")
            println("DEBUG: Сохраненные данные - email: ${userData?.email}, пароль: ${userData?.password}")
            
            if (userData != null && userData.email == email && userData.password == password) {
                // Пользователь найден, вход успешен
                println("DEBUG: Данные совпадают, вход успешен")
                val currentDate = java.util.Date()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSignInSuccessful = true,
                    user = com.drivenext.app.domain.model.User(
                        id = userData.email,
                        email = userData.email,
                        firstName = userData.firstName,
                        lastName = userData.lastName,
                        middleName = userData.middleName,
                        birthDate = java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault()).parse(userData.birthDate) ?: currentDate,
                        gender = if (userData.gender == "Мужской") com.drivenext.app.domain.model.Gender.MALE else com.drivenext.app.domain.model.Gender.FEMALE,
                        profilePhotoUrl = null,
                        driverLicenseNumber = userData.driverLicenseNumber,
                        driverLicenseIssueDate = java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault()).parse(userData.driverLicenseIssueDate),
                        driverLicensePhotoUrl = null,
                        passportPhotoUrl = null,
                        isVerified = false,
                        createdAt = currentDate,
                        updatedAt = currentDate
                    )
                )
                println("DEBUG: Состояние обновлено - isSignInSuccessful: true")
            } else {
                // Неверные данные
                println("DEBUG: Данные не совпадают или пользователь не найден")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Неверный email или пароль"
                )
            }
        }
    }
    
    // Обрабатывает ошибки и устанавливает соответствующие поля ошибок в UI состоянии
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
