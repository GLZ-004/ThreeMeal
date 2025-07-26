package com.threemeal.app.data.dao

import androidx.room.*
import com.threemeal.app.data.entity.MealRecord
import kotlinx.coroutines.flow.Flow

/**
 * 用餐记录数据访问对象
 */
@Dao
interface MealRecordDao {
    
    @Query("SELECT * FROM meal_records ORDER BY date DESC, mealType ASC")
    fun getAllMealRecords(): Flow<List<MealRecord>>
    
    @Query("SELECT * FROM meal_records WHERE date = :date ORDER BY mealType ASC")
    fun getMealRecordsByDate(date: String): Flow<List<MealRecord>>
    
    @Query("SELECT * FROM meal_records WHERE date = :date AND mealType = :mealType")
    suspend fun getMealRecordByDateAndType(date: String, mealType: String): MealRecord?
    
    @Query("SELECT * FROM meal_records WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC, mealType ASC")
    fun getMealRecordsByDateRange(startDate: String, endDate: String): Flow<List<MealRecord>>
    
    @Query("SELECT DISTINCT date FROM meal_records ORDER BY date DESC")
    fun getAllDates(): Flow<List<String>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealRecord(mealRecord: MealRecord): Long
    
    @Update
    suspend fun updateMealRecord(mealRecord: MealRecord)
    
    @Delete
    suspend fun deleteMealRecord(mealRecord: MealRecord)
    
    @Query("DELETE FROM meal_records WHERE date = :date")
    suspend fun deleteMealRecordsByDate(date: String)
    
    @Query("DELETE FROM meal_records WHERE date BETWEEN :startDate AND :endDate")
    suspend fun deleteMealRecordsByDateRange(startDate: String, endDate: String)
    
    @Query("SELECT COUNT(*) FROM meal_records WHERE date = :date")
    suspend fun getMealRecordCountByDate(date: String): Int
} 