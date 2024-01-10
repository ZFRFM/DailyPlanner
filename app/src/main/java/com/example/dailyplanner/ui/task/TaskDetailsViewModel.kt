package com.example.dailyplanner.ui.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.data.TasksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TaskDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
): ViewModel() {
    private val taskId: Int = checkNotNull(savedStateHandle[TaskDetailsDestination.taskIdArg])

    val uiState: StateFlow<TaskDetailsUiState> =
        tasksRepository.getTaskStream(taskId)
            .filterNotNull()
            .map {
                TaskDetailsUiState(taskDetails = it.toTaskDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TaskDetailsUiState()
            )

    suspend fun deleteTask() {
        tasksRepository.deleteTask(uiState.value.taskDetails.toTask())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TaskDetailsUiState(
    val taskDetails: TaskDetails = TaskDetails()
)