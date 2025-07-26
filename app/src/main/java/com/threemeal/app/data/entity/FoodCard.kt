package com.threemeal.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 食物卡片实体类
 */
@Entity(tableName = "food_cards")
data class FoodCard(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,           // 食物名称
    val imageUri: String,       // 图片URI
    val price: Float,           // 价格
    val type: String,           // 类型：外卖/自制/堂食
    val note: String? = null,   // 备注
    val location: String? = null, // 地点（堂食时使用）
    val createdAt: Long = System.currentTimeMillis(), // 创建时间
    val updatedAt: Long = System.currentTimeMillis()  // 更新时间
) 