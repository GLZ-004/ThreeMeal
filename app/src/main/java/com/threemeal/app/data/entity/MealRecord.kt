package com.threemeal.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用餐记录实体类
 */
@Entity(tableName = "meal_records")
data class MealRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,           // 日期，格式：yyyy-MM-dd
    val mealType: String,       // 餐次：breakfast/lunch/dinner
    val foodCardIds: String,    // 食物卡片ID列表，JSON格式
    val mood: String,           // 心情
    val note: String? = null,   // 备注
    val createdAt: Long = System.currentTimeMillis(), // 创建时间
    val updatedAt: Long = System.currentTimeMillis()  // 更新时间
) 