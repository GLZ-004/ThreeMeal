package com.threemeal.app.ui.screens.mealrecord

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.threemeal.app.R
import com.threemeal.app.data.entity.FoodCard
import com.threemeal.app.util.Constants
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealRecordScreen(
    mealType: String,
    date: String?,
    onNavigateBack: () -> Unit
) {
    var selectedFoodCards by remember { mutableStateOf(listOf<FoodCard>()) }
    var selectedMood by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    
    val currentDate = date?.let { 
        LocalDate.parse(it, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT))
    } ?: LocalDate.now()
    
    val displayDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
    val mealTypeText = when (mealType) {
        Constants.MEAL_BREAKFAST -> stringResource(R.string.breakfast)
        Constants.MEAL_LUNCH -> stringResource(R.string.lunch)
        Constants.MEAL_DINNER -> stringResource(R.string.dinner)
        else -> mealType
    }
    
    // 模拟食物卡片数据
    val availableFoodCards = remember {
        listOf(
            FoodCard(
                id = 1,
                name = "宫保鸡丁",
                imageUri = "https://via.placeholder.com/150",
                price = 25.0f,
                type = Constants.FOOD_TYPE_TAKEOUT
            ),
            FoodCard(
                id = 2,
                name = "红烧肉",
                imageUri = "https://via.placeholder.com/150",
                price = 35.0f,
                type = Constants.FOOD_TYPE_HOMEMADE
            ),
            FoodCard(
                id = 3,
                name = "麻婆豆腐",
                imageUri = "https://via.placeholder.com/150",
                price = 18.0f,
                type = Constants.FOOD_TYPE_DINE_IN
            ),
            FoodCard(
                id = 4,
                name = "糖醋里脊",
                imageUri = "https://via.placeholder.com/150",
                price = 28.0f,
                type = Constants.FOOD_TYPE_TAKEOUT
            )
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$displayDate $mealTypeText") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            // 保存记录
                            onNavigateBack()
                        }
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 选择食物卡片
            FoodCardSelector(
                availableFoodCards = availableFoodCards,
                selectedFoodCards = selectedFoodCards,
                onFoodCardSelected = { foodCard ->
                    selectedFoodCards = if (selectedFoodCards.contains(foodCard)) {
                        selectedFoodCards - foodCard
                    } else {
                        selectedFoodCards + foodCard
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 心情选择
            MoodSelector(
                selectedMood = selectedMood,
                onMoodSelected = { selectedMood = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 备注输入
            NoteInput(
                note = note,
                onNoteChanged = { note = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 预览
            if (selectedFoodCards.isNotEmpty() || selectedMood.isNotEmpty() || note.isNotEmpty()) {
                RecordPreview(
                    selectedFoodCards = selectedFoodCards,
                    selectedMood = selectedMood,
                    note = note
                )
            }
        }
    }
}

@Composable
fun FoodCardSelector(
    availableFoodCards: List<FoodCard>,
    selectedFoodCards: List<FoodCard>,
    onFoodCardSelected: (FoodCard) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.select_food_cards),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(availableFoodCards) { foodCard ->
                FoodCardSelectorItem(
                    foodCard = foodCard,
                    isSelected = selectedFoodCards.contains(foodCard),
                    onClick = { onFoodCardSelected(foodCard) }
                )
            }
        }
        
        if (selectedFoodCards.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "已选择:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedFoodCards) { foodCard ->
                    SelectedFoodCardChip(
                        foodCard = foodCard,
                        onRemove = { onFoodCardSelected(foodCard) }
                    )
                }
            }
        }
    }
}

@Composable
fun FoodCardSelectorItem(
    foodCard: FoodCard,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            Column {
                AsyncImage(
                    model = foodCard.imageUri,
                    contentDescription = foodCard.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = foodCard.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = "¥${foodCard.price}",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectedFoodCardChip(
    foodCard: FoodCard,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = foodCard.name,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.width(4.dp))
            
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "移除",
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun MoodSelector(
    selectedMood: String,
    onMoodSelected: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.select_mood),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MoodChip(
                text = stringResource(R.string.mood_happy),
                isSelected = selectedMood == Constants.MOOD_HAPPY,
                onClick = { onMoodSelected(Constants.MOOD_HAPPY) },
                color = Color(0xFF34C759)
            )
            
            MoodChip(
                text = stringResource(R.string.mood_normal),
                isSelected = selectedMood == Constants.MOOD_NORMAL,
                onClick = { onMoodSelected(Constants.MOOD_NORMAL) },
                color = Color(0xFFFF9500)
            )
            
            MoodChip(
                text = stringResource(R.string.mood_terrible),
                isSelected = selectedMood == Constants.MOOD_TERRIBLE,
                onClick = { onMoodSelected(Constants.MOOD_TERRIBLE) },
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

@Composable
fun NoteInput(
    note: String,
    onNoteChanged: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.meal_note),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = note,
            onValueChange = onNoteChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("添加用餐备注...") },
            minLines = 3,
            maxLines = 5
        )
    }
}

@Composable
fun RecordPreview(
    selectedFoodCards: List<FoodCard>,
    selectedMood: String,
    note: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "预览",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (selectedFoodCards.isNotEmpty()) {
                Text(
                    text = "食物: ${selectedFoodCards.joinToString(", ") { it.name }}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            if (selectedMood.isNotEmpty()) {
                Text(
                    text = "心情: $selectedMood",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            if (note.isNotEmpty()) {
                Text(
                    text = "备注: $note",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
} 