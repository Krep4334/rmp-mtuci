package com.drivenext.app.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewModel для экрана регистрации (второй шаг)
 */
class SignUpStep2ViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(SignUpStep2UiState())
    val uiState: StateFlow<SignUpStep2UiState> = _uiState.asStateFlow()
    
    fun validateStep2(
        lastName: String,
        firstName: String,
        middleName: String,
        birthDate: String,
        gender: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                lastNameError = null,
                firstNameError = null,
                birthDateError = null,
                genderError = null,
                errorMessage = null
            )
            
            // Валидация фамилии
            if (lastName.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    lastNameError = "Фамилия обязательна для заполнения"
                )
                return@launch
            }
            
            // Валидация имени
            if (firstName.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    firstNameError = "Имя обязательно для заполнения"
                )
                return@launch
            }
            
            // Валидация даты рождения
            if (birthDate.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    birthDateError = "Дата рождения обязательна для заполнения"
                )
                return@launch
            }
            
            if (!isValidBirthDate(birthDate)) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    birthDateError = "Введите корректную дату рождения."
                )
                return@launch
            }
            
            // Валидация пола
            if (gender.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    genderError = "Пол обязателен для заполнения"
                )
                return@launch
            }
            
            // Если все валидации прошли успешно
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isStep2Valid = true
            )
        }
    }
    
    private fun isValidBirthDate(dateString: String): Boolean {
        return try {
            val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            format.isLenient = false
            val date = format.parse(dateString)
            
            if (date == null) return false
            
            val calendar = Calendar.getInstance()
            calendar.time = date
            
            val year = calendar.get(Calendar.YEAR)
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            
            // Проверяем, что дата не в будущем и не слишком далеко в прошлом
            year in 1900..currentYear
        } catch (e: Exception) {
            false
        }
    }
}

data class SignUpStep2UiState(
    val isLoading: Boolean = false,
    val isStep2Valid: Boolean = false,
    val lastNameError: String? = null,
    val firstNameError: String? = null,
    val birthDateError: String? = null,
    val genderError: String? = null,
    val errorMessage: String? = null
)
