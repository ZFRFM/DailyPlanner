package com.example.dailyplanner

import android.app.Application
import com.example.dailyplanner.data.AppContainer
import com.example.dailyplanner.data.AppDataContainer

class DailyPlannerApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}