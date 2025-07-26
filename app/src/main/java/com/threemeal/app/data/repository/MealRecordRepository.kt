package com.threemeal.app.data.repository

import com.threemeal.app.data.dao.MealRecordDao
import com.threemeal.app.data.entity.MealRecord
import kotlinx.coroutines.flow.Flow

/**
 * 用餐记录数据仓库
 */
class MealRecordRepository constructor(
    private val mealRecordDao: MealRecordDao
) {
    
    fun getAllMealRecords(): Flow<List<MealRecord>> = mealRecordDao.getAllMealRecords()
    
    fun getMealRecordsByDate(date: String): Flow<List<MealRecord>> = mealRecordDao.getMealRecordsByDate(date)
    
    suspend fun getMealRecordByDateAndType(date: String, mealType: String): MealRecord? = 
        mealRecordDao.getMealRecordByDateAndType(date, mealType)
    
    fun getMealRecordsByDateRange(startDate: String, endDate: String): Flow<List<MealRecord>> = 
        mealRecordDao.getMealRecordsByDateRange(startDate, endDate)
    
    fun getAllDates(): Flow<List<String>> = mealRecordDao.getAllDates()
    
    suspend fun insertMealRecord(mealRecord: MealRecord): Long = mealRecordDao.insertMealRecord(mealRecord)
    
    suspend fun updateMealRecord(mealRecord: MealRecord) = mealRecordDao.updateMealRecord(mealRecord)
    
    suspend fun deleteMealRecord(mealRecord: MealRecord) = mealRecordDao.deleteMealRecord(mealRecord)
    
    suspend fun deleteMealRecordsByDate(date: String) = mealRecordDao.deleteMealRecordsByDate(date)
    
    suspend fun deleteMealRecordsByDateRange(startDate: String, endDate: String) = 
        mealRecordDao.deleteMealRecordsByDateRange(startDate, endDate)
    
    suspend fun getMealRecordCountByDate(date: String): Int = mealRecordDao.getMealRecordCountByDate(date)
} 