package com.example.dailyplanner.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.data.TasksRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TaskEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
): ViewModel() {
    private val taskId: Int = checkNotNull(savedStateHandle[TaskEditDestination.taskIdArg])

    init {
        viewModelScope.launch {
            taskUiState = tasksRepository.getTaskStream(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState(true)
        }
    }

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && description.isNotBlank()
        }
    }

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState = TaskUiState(
            taskDetails = taskDetails,
            isEntryValid = validateInput(taskDetails)
        )
    }

    suspend fun updateTask() {
        if (validateInput(taskUiState.taskDetails)) {
            tasksRepository.updateTask(taskUiState.taskDetails.toTask())
        }
    }
}