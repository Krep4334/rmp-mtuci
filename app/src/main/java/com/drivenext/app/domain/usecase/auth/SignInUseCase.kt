package com.drivenext.app.domain.usecase.auth

import com.drivenext.app.domain.model.User
import com.drivenext.app.domain.repository.AuthRepository
import com.drivenext.app.domain.util.Resource

/**
 * Use case для входа пользователя в систему
 */
class SignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        if (email.isBlank()) {
            return Resource.Error("Email не может быть пустым")
        }
        
        if (password.isBlank()) {
            return Resource.Error("Пароль не может быть пустым")
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Resource.Error("Неверный формат email")
        }
        
        return authRepository.signIn(email, password)
    }
}
