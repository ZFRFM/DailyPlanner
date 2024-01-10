package com.example.dailyplanner.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dailyplanner.DailyPlannerApplication
import com.example.dailyplanner.ui.home.HomeViewModel
import com.example.dailyplanner.ui.task.TaskDetailsViewModel
import com.example.dailyplanner.ui.task.TaskEditViewModel
import com.example.dailyplanner.ui.task.TaskEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                dailyPlannerApplication().container.tasksRepository
            )
        }

        initializer {
            TaskDetailsViewModel(
                this.createSavedStateHandle(),
                dailyPlannerApplication().container.tasksRepository
            )
        }

        initializer {
            TaskEntryViewModel(
                dailyPlannerApplication().container.tasksRepository
            )
        }

        initializer {
            TaskEditViewModel(
                this.createSavedStateHandle(),
                dailyPlannerApplication().container.tasksRepository
            )
        }
    }
}

fun CreationExtras.dailyPlannerApplication(): DailyPlannerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DailyPlannerApplication)