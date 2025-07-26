package com.threemeal.app.data.repository

import com.threemeal.app.data.dao.FoodCardDao
import com.threemeal.app.data.entity.FoodCard
import kotlinx.coroutines.flow.Flow

/**
 * 食物卡片数据仓库
 */
class FoodCardRepository constructor(
    private val foodCardDao: FoodCardDao
) {
    
    fun getAllFoodCards(): Flow<List<FoodCard>> = foodCardDao.getAllFoodCards()
    
    fun getFoodCardsByType(type: String): Flow<List<FoodCard>> = foodCardDao.getFoodCardsByType(type)
    
    suspend fun getFoodCardById(id: Long): FoodCard? = foodCardDao.getFoodCardById(id)
    
    fun searchFoodCards(query: String): Flow<List<FoodCard>> = foodCardDao.searchFoodCards(query)
    
    suspend fun insertFoodCard(foodCard: FoodCard): Long = foodCardDao.insertFoodCard(foodCard)
    
    suspend fun updateFoodCard(foodCard: FoodCard) = foodCardDao.updateFoodCard(foodCard)
    
    suspend fun deleteFoodCard(foodCard: FoodCard) = foodCardDao.deleteFoodCard(foodCard)
    
    suspend fun deleteFoodCardsByIds(ids: List<Long>) = foodCardDao.deleteFoodCardsByIds(ids)
    
    suspend fun getFoodCardCount(): Int = foodCardDao.getFoodCardCount()
} 