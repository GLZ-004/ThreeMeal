package com.threemeal.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.threemeal.app.ui.screens.calendar.CalendarScreen
import com.threemeal.app.ui.screens.foodcards.FoodCardsScreen
import com.threemeal.app.ui.screens.home.HomeScreen
import com.threemeal.app.ui.screens.mealrecord.MealRecordScreen
import com.threemeal.app.util.Constants

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Constants.ROUTE_HOME
    ) {
        composable(Constants.ROUTE_HOME) {
            HomeScreen(
                onNavigateToFoodCards = {
                    navController.navigate(Constants.ROUTE_FOOD_CARDS)
                },
                onNavigateToCalendar = {
                    navController.navigate(Constants.ROUTE_CALENDAR)
                },
                onNavigateToMealRecord = { mealType ->
                    navController.navigate("${Constants.ROUTE_MEAL_RECORD}?${Constants.PARAM_MEAL_TYPE}=$mealType")
                }
            )
        }
        
        composable(Constants.ROUTE_FOOD_CARDS) {
            FoodCardsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToFoodCardDetail = { foodCardId ->
                    navController.navigate("${Constants.ROUTE_FOOD_CARD_DETAIL}?${Constants.PARAM_FOOD_CARD_ID}=$foodCardId")
                }
            )
        }
        
        composable(Constants.ROUTE_CALENDAR) {
            CalendarScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToMealRecord = { date, mealType ->
                    navController.navigate("${Constants.ROUTE_MEAL_RECORD}?${Constants.PARAM_DATE}=$date&${Constants.PARAM_MEAL_TYPE}=$mealType")
                }
            )
        }
        
        composable(
            route = "${Constants.ROUTE_MEAL_RECORD}?${Constants.PARAM_MEAL_TYPE}={${Constants.PARAM_MEAL_TYPE}}&${Constants.PARAM_DATE}={${Constants.PARAM_DATE}}"
        ) { backStackEntry ->
            val mealType = backStackEntry.arguments?.getString(Constants.PARAM_MEAL_TYPE) ?: ""
            val date = backStackEntry.arguments?.getString(Constants.PARAM_DATE)
            
            MealRecordScreen(
                mealType = mealType,
                date = date,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 