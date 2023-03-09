package com.example.myapplication.network

import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getConvertedRate(to: String) =
        apiService.convertCurrency(to)

}