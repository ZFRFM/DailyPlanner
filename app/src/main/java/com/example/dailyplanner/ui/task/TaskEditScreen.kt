package com.example.dailyplanner.ui.task

import com.example.dailyplanner.R
import com.example.dailyplanner.ui.navigation.NavigationDestination

object TaskEditDestination: NavigationDestination {
    override val route = "task_edit"
    override val titleRes = R.string.edit_task_title
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

class TaskEditScreen {
}