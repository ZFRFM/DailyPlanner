package com.example.dailyplanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(TaskConverter::class)
abstract class DailyPlannerDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: DailyPlannerDatabase? = null

        fun getDatabase(context: Context): DailyPlannerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DailyPlannerDatabase::class.java, "task_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}