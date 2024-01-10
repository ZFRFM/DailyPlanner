package com.example.dailyplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dailyplanner.ui.home.HomeDestination
import com.example.dailyplanner.ui.home.HomeScreen
import com.example.dailyplanner.ui.task.TaskDetailsDestination
import com.example.dailyplanner.ui.task.TaskDetailsScreen
import com.example.dailyplanner.ui.task.TaskEditDestination
import com.example.dailyplanner.ui.task.TaskEditScreen
import com.example.dailyplanner.ui.task.TaskEntryDestination
import com.example.dailyplanner.ui.task.TaskEntryScreen

@Composable
fun DailyPlannerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen()
        }

        composable(
            route = TaskDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(TaskDetailsDestination.taskIdArg) {
                type = NavType.IntType
            })
        ) {
            TaskDetailsScreen()
        }

        composable(
            route = TaskEditDestination.routeWithArgs,
            arguments = listOf(navArgument(TaskEditDestination.taskIdArg) {
                type = NavType.IntType
            })
        ) {
            TaskEditScreen()
        }

        composable(route = TaskEntryDestination.route) {
            TaskEntryScreen()
        }
    }
}