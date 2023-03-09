package com.example.myapplication.datamanager

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PreferenceConnector {

    private val PREF_NAME = "SAMPLE_PREFS"

    companion object {
        const val IS_LOGGED_IN = "isLoggedIn"
        const val USER_EMAIL = "email"
        const val USER_PASSWORD = "password"
    }


    @Provides
    fun provideSharedPreferenceInstance(application: Application): SharedPreferences {
        return application.getSharedPreferences("preferences_name", Context.MODE_PRIVATE)
    }
}