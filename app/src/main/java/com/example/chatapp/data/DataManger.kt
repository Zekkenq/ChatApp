package com.example.chatapp.data

import android.content.Context
import android.content.SharedPreferences

class DataManager(baseContext: Context) {

    private val shared: SharedPreferences

    init {
        shared = baseContext.getSharedPreferences("private", Context.MODE_PRIVATE)
    }
}