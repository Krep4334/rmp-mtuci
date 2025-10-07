package com.drivenext.app.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drivenext.app.presentation.screens.auth.SignInViewModel
import com.drivenext.app.presentation.screens.auth.SignUpStep3ViewModel

/**
 * Фабрика для создания ViewModels с зависимостями
 */
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(context) as T
            }
            modelClass.isAssignableFrom(SignUpStep3ViewModel::class.java) -> {
                SignUpStep3ViewModel(context) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
