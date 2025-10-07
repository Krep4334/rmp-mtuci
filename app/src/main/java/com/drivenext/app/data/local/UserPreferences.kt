package com.drivenext.app.data.local

import android.content.Context
import android.content.SharedPreferences

/**
 * Модель данных пользователя для локального хранения
 */
data class UserData(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val birthDate: String,
    val gender: String,
    val driverLicenseNumber: String,
    val driverLicenseIssueDate: String
)

/**
 * UserPreferences для хранения данных пользователя
 */
class UserPreferences(context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "user_preferences", 
        Context.MODE_PRIVATE
    )
    
    // Сохранение данных пользователя
    fun saveUserData(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String? = null,
        birthDate: String,
        gender: String,
        driverLicenseNumber: String,
        driverLicenseIssueDate: String
    ) {
        println("DEBUG: Сохранение данных - email: $email, password: $password")
        sharedPreferences.edit().apply {
            putString(KEY_EMAIL, email)
            putString(KEY_PASSWORD, password)
            putString(KEY_FIRST_NAME, firstName)
            putString(KEY_LAST_NAME, lastName)
            putString(KEY_MIDDLE_NAME, middleName)
            putString(KEY_BIRTH_DATE, birthDate)
            putString(KEY_GENDER, gender)
            putString(KEY_DRIVER_LICENSE_NUMBER, driverLicenseNumber)
            putString(KEY_DRIVER_LICENSE_ISSUE_DATE, driverLicenseIssueDate)
            putBoolean(KEY_IS_LOGGED_IN, true)
            putLong(KEY_LOGIN_TIMESTAMP, System.currentTimeMillis())
            apply()
        }
        println("DEBUG: Данные сохранены в SharedPreferences")
    }
    
    // Получение данных пользователя
    fun getUserData(): UserData? {
        println("DEBUG: Получение данных пользователя из SharedPreferences")
        val email = sharedPreferences.getString(KEY_EMAIL, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)
        println("DEBUG: Email из SharedPreferences: $email")
        println("DEBUG: Password из SharedPreferences: $password")
        
        if (email == null || password == null) {
            println("DEBUG: Email или пароль не найдены в SharedPreferences")
            return null
        }
        
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, null) ?: return null
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, null) ?: return null
        val middleName = sharedPreferences.getString(KEY_MIDDLE_NAME, null)
        val birthDate = sharedPreferences.getString(KEY_BIRTH_DATE, null) ?: return null
        val gender = sharedPreferences.getString(KEY_GENDER, null) ?: return null
        val driverLicenseNumber = sharedPreferences.getString(KEY_DRIVER_LICENSE_NUMBER, null) ?: return null
        val driverLicenseIssueDate = sharedPreferences.getString(KEY_DRIVER_LICENSE_ISSUE_DATE, null) ?: return null
        
        println("DEBUG: Все данные найдены, создаем UserData")
        return UserData(
            email = email,
            password = password,
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            birthDate = birthDate,
            gender = gender,
            driverLicenseNumber = driverLicenseNumber,
            driverLicenseIssueDate = driverLicenseIssueDate
        )
    }
    
    // Проверка авторизации
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    
    // Выход из аккаунта
    fun logout() {
        sharedPreferences.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, false)
            putLong(KEY_LOGIN_TIMESTAMP, 0)
            apply()
        }
    }
    
    // Полная очистка данных
    fun clearAllData() {
        println("DEBUG: Очистка всех данных из SharedPreferences")
        sharedPreferences.edit().clear().apply()
    }
    
    // Метод для отладки - показать все сохраненные данные
    fun debugShowAllData() {
        println("DEBUG: === Все данные в SharedPreferences ===")
        println("DEBUG: Email: ${sharedPreferences.getString(KEY_EMAIL, "НЕТ")}")
        println("DEBUG: Password: ${sharedPreferences.getString(KEY_PASSWORD, "НЕТ")}")
        println("DEBUG: FirstName: ${sharedPreferences.getString(KEY_FIRST_NAME, "НЕТ")}")
        println("DEBUG: LastName: ${sharedPreferences.getString(KEY_LAST_NAME, "НЕТ")}")
        println("DEBUG: IsLoggedIn: ${sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)}")
        println("DEBUG: ======================================")
    }
    
    // Получение времени последнего входа
    fun getLastLoginTime(): Long {
        return sharedPreferences.getLong(KEY_LOGIN_TIMESTAMP, 0)
    }
    
    companion object {
        private const val KEY_EMAIL = "user_email"
        private const val KEY_PASSWORD = "user_password"
        private const val KEY_FIRST_NAME = "user_first_name"
        private const val KEY_LAST_NAME = "user_last_name"
        private const val KEY_MIDDLE_NAME = "user_middle_name"
        private const val KEY_BIRTH_DATE = "user_birth_date"
        private const val KEY_GENDER = "user_gender"
        private const val KEY_DRIVER_LICENSE_NUMBER = "user_driver_license_number"
        private const val KEY_DRIVER_LICENSE_ISSUE_DATE = "user_driver_license_issue_date"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_LOGIN_TIMESTAMP = "login_timestamp"
    }
}
