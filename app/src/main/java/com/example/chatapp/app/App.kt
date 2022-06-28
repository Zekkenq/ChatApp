package com.example.chatapp.app

import android.app.Application
import com.example.chatapp.data.DataManager

class App : Application() {

    companion object {
        lateinit var dataManager: DataManager
    }

    override fun onCreate() {
        super.onCreate()

        dataManager = DataManager(baseContext)
    }
}