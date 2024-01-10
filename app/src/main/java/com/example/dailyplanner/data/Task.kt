package com.example.dailyplanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dateStart: Timestamp,
    val dateFinish: Timestamp,
    val name: String,
    val description: String
)