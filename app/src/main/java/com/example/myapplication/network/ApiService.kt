package com.example.myapplication.network

import com.example.myapplication.data.response.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/v6/latest/{userType}")
    suspend fun convertCurrency(
        @Path("userType") userType: String
    ): Response<ApiResponse>
}