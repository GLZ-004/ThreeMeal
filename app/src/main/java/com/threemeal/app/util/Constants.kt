package com.threemeal.app.util

/**
 * 应用常量
 */
object Constants {
    
    // 餐次类型
    const val MEAL_BREAKFAST = "breakfast"
    const val MEAL_LUNCH = "lunch"
    const val MEAL_DINNER = "dinner"
    
    // 食物类型
    const val FOOD_TYPE_TAKEOUT = "外卖"
    const val FOOD_TYPE_HOMEMADE = "自制"
    const val FOOD_TYPE_DINE_IN = "堂食"
    
    // 心情类型
    const val MOOD_HAPPY = "开心"
    const val MOOD_NORMAL = "一般"
    const val MOOD_TERRIBLE = "糟糕"
    
    // 日期格式
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DATE_DISPLAY_FORMAT = "yyyy年MM月dd日"
    const val TIME_FORMAT = "HH:mm"
    
    // 导航路由
    const val ROUTE_HOME = "home"
    const val ROUTE_FOOD_CARDS = "food_cards"
    const val ROUTE_CALENDAR = "calendar"
    const val ROUTE_MEAL_RECORD = "meal_record"
    const val ROUTE_FOOD_CARD_DETAIL = "food_card_detail"
    const val ROUTE_SETTINGS = "settings"
    
    // 参数
    const val PARAM_MEAL_TYPE = "meal_type"
    const val PARAM_DATE = "date"
    const val PARAM_FOOD_CARD_ID = "food_card_id"
    
    // 图片相关
    const val MAX_IMAGE_SIZE = 1024 * 1024 // 1MB
    const val IMAGE_QUALITY = 80
    
    // 数据库
    const val DATABASE_NAME = "three_meal_database"
    
    // 权限请求码
    const val PERMISSION_REQUEST_CAMERA = 1001
    const val PERMISSION_REQUEST_STORAGE = 1002
    
    // 图片选择请求码
    const val REQUEST_IMAGE_CAPTURE = 2001
    const val REQUEST_IMAGE_PICK = 2002
} 