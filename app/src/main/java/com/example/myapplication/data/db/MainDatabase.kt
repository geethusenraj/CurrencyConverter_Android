package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.data.db.dao.RatesDao
import com.example.myapplication.data.db.entity.RatesEntity

@TypeConverters(RateConverter::class)
@Database(entities = [RatesEntity::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun getRatesDao(): RatesDao

    companion object {
        @Volatile
        private var instance: MainDatabase? = null
        private val LOCK = Any()

        fun getDatabase(context: Context): MainDatabase {
            if (instance != null) {
                return instance!!
            }
            synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
            return instance!!

        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MainDatabase::class.java,
                "MainDatabase.db"
            ).build()
    }
}