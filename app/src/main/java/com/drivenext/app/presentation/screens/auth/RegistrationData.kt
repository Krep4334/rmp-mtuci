package com.drivenext.app.presentation.screens.auth

import android.net.Uri

/**
 * Класс для хранения данных регистрации между шагами
 */
data class RegistrationData(
    // Шаг 1
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isTermsAccepted: Boolean = false,
    
    // Шаг 2
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val birthDate: String = "",
    val gender: String = "",
    
    // Шаг 3
    val profilePhotoUri: Uri? = null,
    val driverLicenseNumber: String = "",
    val driverLicenseIssueDate: String = "",
    val driverLicensePhotoUri: Uri? = null,
    val passportPhotoUri: Uri? = null
)
