package com.threemeal.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.threemeal.app.R
import com.threemeal.app.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToFoodCards: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToMealRecord: (String) -> Unit
) {
    val today = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
    val displayDate = today.format(dateFormatter)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = onNavigateToFoodCards) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = stringResource(R.string.food_cards)
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 日期显示
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToCalendar() }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = displayDate,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = stringResource(R.string.calendar),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 三餐按钮
            MealButtons(
                onMealClick = onNavigateToMealRecord
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 心情选择
            MoodSelector()
        }
    }
}

@Composable
fun MealButtons(
    onMealClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MealButton(
            title = stringResource(R.string.breakfast),
            icon = Icons.Default.WbSunny,
            onClick = { onMealClick(Constants.MEAL_BREAKFAST) }
        )
        
        MealButton(
            title = stringResource(R.string.lunch),
            icon = Icons.Default.Restaurant,
            onClick = { onMealClick(Constants.MEAL_LUNCH) }
        )
        
        MealButton(
            title = stringResource(R.string.dinner),
            icon = Icons.Default.NightsStay,
            onClick = { onMealClick(Constants.MEAL_DINNER) }
        )
    }
}

@Composable
fun MealButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun MoodSelector() {
    var selectedMood by remember { mutableStateOf("") }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.select_mood),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MoodChip(
                text = stringResource(R.string.mood_happy),
                isSelected = selectedMood == Constants.MOOD_HAPPY,
                onClick = { selectedMood = Constants.MOOD_HAPPY },
                color = Color(0xFF34C759)
            )
            
            MoodChip(
                text = stringResource(R.string.mood_normal),
                isSelected = selectedMood == Constants.MOOD_NORMAL,
                onClick = { selectedMood = Constants.MOOD_NORMAL },
                color = Color(0xFFFF9500)
            )
            
            MoodChip(
                text = stringResource(R.string.mood_terrible),
                isSelected = selectedMood == Constants.MOOD_TERRIBLE,
                onClick = { selectedMood = Constants.MOOD_TERRIBLE },
                color = Color(0xFFFF3B30)
            )
        }
    }
}

@Composable
fun MoodChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    color: Color
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) color else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
    }
} 