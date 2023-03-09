package com.example.myapplication.data.db

import androidx.room.TypeConverter
import com.example.myapplication.data.response.Rates
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RateConverter {
    @TypeConverter
    fun toRatesPojo(rates:String):Rates{
        val type=object : TypeToken<Rates>(){}.type
        return Gson().fromJson(rates,type)
    }
    @TypeConverter
    fun toRatesJson(rates:Rates):String{
        val type=object :TypeToken<Rates>(){}.type
        return Gson().toJson(rates,type)
    }
}