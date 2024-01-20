package com.example.dailyplanner.data

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TaskConverter {
    @TypeConverter
    fun fromTask(task: Task): String {
        return Json.encodeToString(task)
    }

    @TypeConverter
    fun toTask(task: String): Task {
        return Json.decodeFromString(task)
    }
}