package com.example.dailyplanner.ui.task

import com.example.dailyplanner.R
import com.example.dailyplanner.ui.navigation.NavigationDestination

object  TaskEntryDestination: NavigationDestination {
    override val route: String = "task_entry"
    override val titleRes: Int = R.string.task_entry_title
}

class TaskEntryScreen {
}