package com.example.piknikuy

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        private lateinit var appContext: Context
        val context: Context
            get() = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}