package com.drivenext.app.presentation.screens.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Общий ViewModel для всех шагов регистрации
 */
class RegistrationViewModel : ViewModel() {
    
    private val _registrationData = MutableStateFlow(RegistrationData())
    val registrationData: StateFlow<RegistrationData> = _registrationData.asStateFlow()
    
    fun updateStep1Data(
        email: String,
        password: String,
        confirmPassword: String,
        isTermsAccepted: Boolean
    ) {
        _registrationData.value = _registrationData.value.copy(
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            isTermsAccepted = isTermsAccepted
        )
    }
    
    fun updateStep2Data(
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        gender: String
    ) {
        _registrationData.value = _registrationData.value.copy(
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            birthDate = birthDate,
            gender = gender
        )
    }
    
    fun updateStep3Data(
        profilePhotoUri: android.net.Uri?,
        driverLicenseNumber: String,
        driverLicenseIssueDate: String,
        driverLicensePhotoUri: android.net.Uri?,
        passportPhotoUri: android.net.Uri?
    ) {
        _registrationData.value = _registrationData.value.copy(
            profilePhotoUri = profilePhotoUri,
            driverLicenseNumber = driverLicenseNumber,
            driverLicenseIssueDate = driverLicenseIssueDate,
            driverLicensePhotoUri = driverLicensePhotoUri,
            passportPhotoUri = passportPhotoUri
        )
    }
    
    fun getCurrentData(): RegistrationData {
        return _registrationData.value
    }
    
    fun clearData() {
        _registrationData.value = RegistrationData()
    }
}
