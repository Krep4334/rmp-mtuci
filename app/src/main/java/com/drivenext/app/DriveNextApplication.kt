package com.drivenext.app

import android.app.Application
import com.drivenext.app.di.SimpleDI

/**
 * Основной класс приложения DriveNext
 * Инициализирует простую DI систему
 */
class DriveNextApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        SimpleDI.init(this)
    }
}
