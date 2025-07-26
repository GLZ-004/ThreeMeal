package com.threemeal.app.data.dao

import androidx.room.*
import com.threemeal.app.data.entity.FoodCard
import kotlinx.coroutines.flow.Flow

/**
 * 食物卡片数据访问对象
 */
@Dao
interface FoodCardDao {
    
    @Query("SELECT * FROM food_cards ORDER BY createdAt DESC")
    fun getAllFoodCards(): Flow<List<FoodCard>>
    
    @Query("SELECT * FROM food_cards WHERE type = :type ORDER BY createdAt DESC")
    fun getFoodCardsByType(type: String): Flow<List<FoodCard>>
    
    @Query("SELECT * FROM food_cards WHERE id = :id")
    suspend fun getFoodCardById(id: Long): FoodCard?
    
    @Query("SELECT * FROM food_cards WHERE name LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchFoodCards(query: String): Flow<List<FoodCard>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodCard(foodCard: FoodCard): Long
    
    @Update
    suspend fun updateFoodCard(foodCard: FoodCard)
    
    @Delete
    suspend fun deleteFoodCard(foodCard: FoodCard)
    
    @Query("DELETE FROM food_cards WHERE id IN (:ids)")
    suspend fun deleteFoodCardsByIds(ids: List<Long>)
    
    @Query("SELECT COUNT(*) FROM food_cards")
    suspend fun getFoodCardCount(): Int
} 