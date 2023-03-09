package com.example.myapplication

import android.app.Application
import android.util.Log
import com.example.myapplication.datamanager.PreferenceConnector
import com.example.myapplication.datamanager.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate() {
        super.onCreate()
        Log.d("SApp", "application class onCreate")
        when (preferenceManager.readInt(PreferenceConnector.IS_LOGGED_IN, 0)) {
            0 -> Log.d("SApp", "cache value is 0")
            1 -> Log.d("SApp", "cache value is 1")
        }
    }
}