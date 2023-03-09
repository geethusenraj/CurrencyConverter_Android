package com.example.myapplication.uimodules

import com.example.myapplication.data.response.Rates

interface MainActivityNavigator {
    fun showOfflineRateInfo(rateInfoFromRoom: Rates)
}