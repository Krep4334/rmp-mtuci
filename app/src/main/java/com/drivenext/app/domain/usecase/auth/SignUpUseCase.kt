package com.drivenext.app.domain.usecase.auth

import com.drivenext.app.domain.repository.AuthRepository
import com.drivenext.app.domain.util.Resource

/**
 * Use case для регистрации пользователя (первый шаг)
 */
class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): Resource<String> {
        
        if (email.isBlank()) {
            return Resource.Error("Email не может быть пустым")
        }
        
        if (password.isBlank()) {
            return Resource.Error("Пароль не может быть пустым")
        }
        
        if (confirmPassword.isBlank()) {
            return Resource.Error("Подтверждение пароля не может быть пустым")
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Resource.Error("Неверный формат email")
        }
        
        if (password.length < 8) {
            return Resource.Error("Пароль должен содержать минимум 8 символов")
        }
        
        if (password != confirmPassword) {
            return Resource.Error("Пароли не совпадают")
        }
        
        return authRepository.signUp(email, password)
    }
}
