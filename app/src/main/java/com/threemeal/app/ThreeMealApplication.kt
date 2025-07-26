package com.threemeal.app

import android.app.Application
import com.threemeal.app.data.AppDatabase

class ThreeMealApplication : Application() {
    
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    
    override fun onCreate() {
        super.onCreate()
        // 可以在这里初始化其他依赖
    }
} 