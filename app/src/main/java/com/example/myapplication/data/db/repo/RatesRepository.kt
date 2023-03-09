package com.example.myapplication.data.db.repo

import com.example.myapplication.data.db.MainDatabase
import com.example.myapplication.data.db.dao.RatesDao
import com.example.myapplication.data.db.entity.RatesEntity
import com.example.myapplication.data.response.Rates

class RatesRepository {
    private lateinit var appDatabase: MainDatabase
    private lateinit var ratesDao: RatesDao

    constructor(appDatabase: MainDatabase?, ratesDao: RatesDao?) : this() {
        if (appDatabase != null) {
            this.appDatabase = appDatabase
        }
        if (ratesDao != null) {
            this.ratesDao = ratesDao
        }
    }

    constructor()

    suspend fun saveRatesDataToRoom(rates: Rates?, baseCode: String) {
        ratesDao.insert(RatesEntity(baseCode,rates))
    }

    suspend fun getRateInfoFromRoom(currencyId: String?) : Rates{
        return ratesDao.getRateInfo(currencyId)
    }
}

