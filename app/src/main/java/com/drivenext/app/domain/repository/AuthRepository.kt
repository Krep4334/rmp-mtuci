package com.drivenext.app.domain.repository

import com.drivenext.app.domain.model.User
import com.drivenext.app.domain.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс репозитория аутентификации
 */
interface AuthRepository {
    
    /**
     * Вход пользователя по email и паролю
     */
    suspend fun signIn(email: String, password: String): Resource<User>
    
    /**
     * Регистрация нового пользователя (шаг 1)
     */
    suspend fun signUp(email: String, password: String): Resource<String>
    
    /**
     * Завершение регистрации с персональными данными
     */
    suspend fun completeRegistration(
        userId: String,
        firstName: String,
        lastName: String,
        middleName: String?,
        birthDate: java.util.Date,
        gender: com.drivenext.app.domain.model.Gender,
        profilePhotoUrl: String?,
        driverLicenseNumber: String?,
        driverLicenseIssueDate: java.util.Date?,
        driverLicensePhotoUrl: String?,
        passportPhotoUrl: String?
    ): Resource<User>
    
    /**
     * Вход через Google OAuth
     */
    suspend fun signInWithGoogle(idToken: String): Resource<User>
    
    /**
     * Выход из системы
     */
    suspend fun signOut(): Resource<Unit>
    
    /**
     * Получение текущего пользователя
     */
    suspend fun getCurrentUser(): Resource<User?>
    
    /**
     * Проверка состояния аутентификации
     */
    fun getAuthState(): Flow<User?>
}
