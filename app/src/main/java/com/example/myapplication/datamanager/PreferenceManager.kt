package com.example.myapplication.datamanager

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun writeString(key: String?, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun readString(key: String?, defValue: String?): String? {
        return sharedPreferences.getString(key, defValue)
    }

    fun writeBoolean(key: String?, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun readBoolean(key: String?, defValue: Boolean): Boolean? {
        return sharedPreferences.getBoolean(key, defValue)
    }

    fun writeInt(key: String?, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun readInt(key: String?, defValue: Int): Int? {
        return sharedPreferences.getInt(key, defValue)
    }

    fun writeLong(key: String?, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun readLong(key: String?, defValue: Long): Long? {
        return sharedPreferences.getLong(key, defValue)
    }

    fun writeFloat(key: String?, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun readFloat(key: String?, defValue: Float): Float? {
        return sharedPreferences.getFloat(key, defValue)
    }

    fun clearPreference() {
        sharedPreferences.edit().clear().apply()
    }
}