package com.threemeal.app.ui.screens.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.threemeal.app.R
import com.threemeal.app.data.entity.MealRecord
import com.threemeal.app.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    onNavigateBack: () -> Unit,
    onNavigateToMealRecord: (String, String) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    
    // 模拟数据，实际应该从ViewModel获取
    val mealRecords = remember {
        listOf(
            MealRecord(
                id = 1,
                date = "2024-01-15",
                mealType = Constants.MEAL_BREAKFAST,
                foodCardIds = "[1,2]",
                mood = Constants.MOOD_HAPPY,
                note = "早餐很丰盛"
            ),
            MealRecord(
                id = 2,
                date = "2024-01-15",
                mealType = Constants.MEAL_LUNCH,
                foodCardIds = "[3]",
                mood = Constants.MOOD_NORMAL,
                note = "午餐一般"
            ),
            MealRecord(
                id = 3,
                date = "2024-01-14",
                mealType = Constants.MEAL_DINNER,
                foodCardIds = "[1,2,3]",
                mood = Constants.MOOD_HAPPY,
                note = "晚餐很满意"
            )
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.calendar)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* 导出功能 */ }) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = stringResource(R.string.export)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 日历选择器（简化版）
            CalendarSelector(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 当日记录
            DailyRecords(
                date = selectedDate.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)),
                mealRecords = mealRecords.filter { it.date == selectedDate.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)) },
                onMealClick = { mealType ->
                    onNavigateToMealRecord(
                        selectedDate.format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)),
                        mealType
                    )
                }
            )
        }
    }
}

@Composable
fun CalendarSelector(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "日历选择器",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 简化的日期选择器
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onDateSelected(selectedDate.minusDays(1)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "前一天"
                    )
                }
                
                Text(
                    text = selectedDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                
                IconButton(
                    onClick = { onDateSelected(selectedDate.plusDays(1)) }
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "后一天"
                    )
                }
            }
        }
    }
}

@Composable
fun DailyRecords(
    date: String,
    mealRecords: List<MealRecord>,
    onMealClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "当日记录",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (mealRecords.isEmpty()) {
            EmptyState()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mealRecords) { mealRecord ->
                    MealRecordItem(
                        mealRecord = mealRecord,
                        onClick = { onMealClick(mealRecord.mealType) }
                    )
                }
            }
        }
    }
}

@Composable
fun MealRecordItem(
    mealRecord: MealRecord,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 餐次图标
            Icon(
                imageVector = when (mealRecord.mealType) {
                    Constants.MEAL_BREAKFAST -> Icons.Default.WbSunny
                    Constants.MEAL_LUNCH -> Icons.Default.Restaurant
                    Constants.MEAL_DINNER -> Icons.Default.NightsStay
                    else -> Icons.Default.Restaurant
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 餐次信息
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = when (mealRecord.mealType) {
                        Constants.MEAL_BREAKFAST -> stringResource(R.string.breakfast)
                        Constants.MEAL_LUNCH -> stringResource(R.string.lunch)
                        Constants.MEAL_DINNER -> stringResource(R.string.dinner)
                        else -> mealRecord.mealType
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "心情: ${mealRecord.mood}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                mealRecord.note?.let { note ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "备注: $note",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Restaurant,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "暂无记录",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
} 