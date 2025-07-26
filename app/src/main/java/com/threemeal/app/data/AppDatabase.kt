package com.threemeal.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.threemeal.app.data.dao.FoodCardDao
import com.threemeal.app.data.dao.MealRecordDao
import com.threemeal.app.data.entity.FoodCard
import com.threemeal.app.data.entity.MealRecord

/**
 * 应用数据库
 */
@Database(
    entities = [FoodCard::class, MealRecord::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun foodCardDao(): FoodCardDao
    abstract fun mealRecordDao(): MealRecordDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "three_meal_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 