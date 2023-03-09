package com.example.myapplication.data.db.dao

import android.util.Log
import androidx.room.*
import com.example.myapplication.data.db.entity.RatesEntity
import com.example.myapplication.data.response.Rates

@Dao
interface RatesDao {

    @Transaction
    suspend fun upsert(entity: RatesEntity) {
        val exist =
            insert(entity)
        Log.e("ROOM_DB", "insert = $exist")
        if (exist == -1L) {
            Log.e(
                "ROOM_DB", "insert = $exist , currencyId = ${entity.currencyId}," +
                        " currencyRate = ${entity.currencyRate}"
            )
            // exist
//            updateRateTable(entity.u, entity.currencyRate)
        }
    }

    fun updateRateTable(currencyId: String, currencyRate: Double?) {

    }

    /**
     * Insert an array of objects in the database.
     * @return The SQLite row ids
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: RatesEntity): Long

    @Query("SELECT currencyRate FROM RatesTable WHERE currencyId =:currencyId")
    suspend fun getRateInfo(currencyId: String?) : Rates

}