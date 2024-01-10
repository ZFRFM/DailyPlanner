package com.example.dailyplanner.ui.task

import com.example.dailyplanner.R
import com.example.dailyplanner.ui.navigation.NavigationDestination

object TaskDetailsDestination: NavigationDestination {
    override val route: String = "task_details"
    override val titleRes: Int = R.string.task_detail_title
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

class TaskDetailsScreen {
}