package com.example.dailyplanner.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Entity(tableName = "tasks")
@Serializable
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "date_start") val dateStart: Long?,
    @ColumnInfo(name = "date_finish") val dateFinish: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String
)