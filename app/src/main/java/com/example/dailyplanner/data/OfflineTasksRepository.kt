package com.example.dailyplanner.data

import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(private val taskDao: TaskDao): TasksRepository {
    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTaskStream(id: Int): Flow<Task?> = taskDao.getTask(id)

    override suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    override suspend fun updateTask(task: Task) = taskDao.updateTask(task)
}