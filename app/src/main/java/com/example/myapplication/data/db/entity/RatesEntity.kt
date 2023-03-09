package com.example.myapplication.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.data.response.Rates


@Entity(tableName = "RatesTable")
class RatesEntity(
    @PrimaryKey
    @ColumnInfo(name = "currencyId")
    var currencyId: String = "AFN",

    @ColumnInfo(name = "currencyRate")
    var currencyRate: Rates? = null
)