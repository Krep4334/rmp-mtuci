package com.drivenext.app.presentation.screens.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drivenext.app.data.local.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewModel для экрана регистрации (третий шаг)
 */
class SignUpStep3ViewModel(
    private val context: Context
) : ViewModel() {
    
    private val userPreferences = UserPreferences(context)
    
    private val _uiState = MutableStateFlow(SignUpStep3UiState())
    val uiState: StateFlow<SignUpStep3UiState> = _uiState.asStateFlow()
    
    fun completeRegistration(
        registrationData: RegistrationData
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                driverLicenseNumberError = null,
                driverLicenseIssueDateError = null,
                errorMessage = null
            )
            
            // Валидация номера водительского удостоверения
            if (registrationData.driverLicenseNumber.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    driverLicenseNumberError = "Номер водительского удостоверения обязателен"
                )
                return@launch
            }
            
            // Валидация даты выдачи ВУ
            if (registrationData.driverLicenseIssueDate.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    driverLicenseIssueDateError = "Дата выдачи обязательна"
                )
                return@launch
            }
            
            if (!isValidIssueDate(registrationData.driverLicenseIssueDate)) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    driverLicenseIssueDateError = "Введите корректную дату выдачи."
                )
                return@launch
            }
            
            // Валидация загруженных фото
            if (registrationData.driverLicensePhotoUri == null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Пожалуйста, загрузите все необходимые фото."
                )
                return@launch
            }
            
            if (registrationData.passportPhotoUri == null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Пожалуйста, загрузите все необходимые фото."
                )
                return@launch
            }
            
            // Сохранение данных пользователя
            try {
                userPreferences.saveUserData(
                    email = registrationData.email,
                    password = registrationData.password,
                    firstName = registrationData.firstName,
                    lastName = registrationData.lastName,
                    middleName = registrationData.middleName,
                    birthDate = registrationData.birthDate,
                    gender = registrationData.gender,
                    driverLicenseNumber = registrationData.driverLicenseNumber,
                    driverLicenseIssueDate = registrationData.driverLicenseIssueDate
                )
                
                // Проверяем, что данные сохранились
                userPreferences.debugShowAllData()
                val savedData = userPreferences.getUserData()
                println("DEBUG: Данные сохранены - email: ${savedData?.email}, пароль: ${savedData?.password}")
                
                // Имитация сетевого запроса
                kotlinx.coroutines.delay(1000)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRegistrationSuccessful = true
                )
            } catch (e: Exception) {
                println("DEBUG: Ошибка при сохранении данных: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Ошибка при завершении регистрации. Попробуйте снова."
                )
            }
        }
    }
    
    private fun isValidIssueDate(dateString: String): Boolean {
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
            year in 1950..currentYear
        } catch (e: Exception) {
            false
        }
    }
}

data class SignUpStep3UiState(
    val isLoading: Boolean = false,
    val isRegistrationSuccessful: Boolean = false,
    val driverLicenseNumberError: String? = null,
    val driverLicenseIssueDateError: String? = null,
    val errorMessage: String? = null
)
