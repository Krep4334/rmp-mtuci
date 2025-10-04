package com.drivenext.app.di

import android.content.Context
import com.drivenext.app.data.remote.SupabaseClient
import com.drivenext.app.data.repository.AuthRepositoryImpl
import com.drivenext.app.domain.repository.AuthRepository
import com.drivenext.app.domain.usecase.auth.SignInUseCase
import com.drivenext.app.domain.usecase.auth.SignUpUseCase
import com.drivenext.app.presentation.util.NetworkConnectivityObserver

/**
 * Простая DI система без аннотаций (заменяет Hilt временно)
 */
object SimpleDI {
    
    private lateinit var appContext: Context
    
    fun init(context: Context) {
        appContext = context.applicationContext
    }
    
    // Repositories
    private val supabaseClient by lazy { SupabaseClient() }
    private val authRepository: AuthRepository by lazy { AuthRepositoryImpl(supabaseClient) }
    
    // Use Cases
    val signInUseCase by lazy { SignInUseCase(authRepository) }
    val signUpUseCase by lazy { SignUpUseCase(authRepository) }
    
    // Utils
    val networkConnectivityObserver by lazy { NetworkConnectivityObserver(appContext) }
    
    // Provide methods
    fun provideAuthRepository(): AuthRepository = authRepository
    fun provideNetworkConnectivityObserver(): NetworkConnectivityObserver = networkConnectivityObserver
}

