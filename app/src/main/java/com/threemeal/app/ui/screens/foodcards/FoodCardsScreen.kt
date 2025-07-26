package com.threemeal.app.ui.screens.foodcards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCardsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToFoodCardDetail: (Long) -> Unit
) {
    var isSelectionMode by remember { mutableStateOf(false) }
    var selectedCards by remember { mutableStateOf(setOf<Long>()) }
    
    // 模拟数据，实际应该从ViewModel获取
    val foodCards = remember {
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
            )
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.food_cards)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    if (isSelectionMode) {
                        IconButton(
                            onClick = {
                                // 删除选中的卡片
                                selectedCards.clear()
                                isSelectionMode = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { isSelectionMode = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.SelectAll,
                                contentDescription = stringResource(R.string.select)
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* 添加新食物卡片 */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(foodCards) { foodCard ->
                FoodCardItem(
                    foodCard = foodCard,
                    isSelected = selectedCards.contains(foodCard.id),
                    isSelectionMode = isSelectionMode,
                    onCardClick = {
                        if (isSelectionMode) {
                            selectedCards = if (selectedCards.contains(foodCard.id)) {
                                selectedCards - foodCard.id
                            } else {
                                selectedCards + foodCard.id
                            }
                        } else {
                            onNavigateToFoodCardDetail(foodCard.id)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FoodCardItem(
    foodCard: FoodCard,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            Column {
                // 食物图片
                AsyncImage(
                    model = foodCard.imageUri,
                    contentDescription = foodCard.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // 食物信息
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = foodCard.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "¥${foodCard.price}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = foodCard.type,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // 选择指示器
            if (isSelectionMode) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                        )
                    ) {
                        Icon(
                            imageVector = if (isSelected) Icons.Default.Check else Icons.Default.RadioButtonUnchecked,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
} 