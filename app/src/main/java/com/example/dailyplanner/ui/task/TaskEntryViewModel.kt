package com.example.dailyplanner.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dailyplanner.data.Task
import com.example.dailyplanner.data.TasksRepository
import java.sql.Timestamp
import java.util.Calendar

class TaskEntryViewModel(
    private val tasksRepository: TasksRepository
): ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState = TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && description.isNotBlank()
        }
    }

    suspend fun saveTask() {
        if (validateInput()) {
            tasksRepository.insertTask(taskUiState.taskDetails.toTask())
        }
    }
}

data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isEntryValid: Boolean = false
)

data class TaskDetails(
    val id: Int = 0,
    val dateStart: Long? = 0,
    val dateFinish: Long? = 0,
    val name: String = "",
    val description: String = ""
)

fun Task.toTaskDetails(): TaskDetails = TaskDetails(
    id = id,
    dateStart = dateStart,
    dateFinish = dateFinish,
    name = name,
    description = description
)

fun Task.toTaskUiState(isEntryValid: Boolean = false): TaskUiState = TaskUiState(
    taskDetails = this.toTaskDetails(),
    isEntryValid = isEntryValid
)

fun TaskDetails.toTask(): Task = Task(
    id = id,
    dateStart = dateStart,
    dateFinish = dateFinish,
    name = name,
    description = description
)