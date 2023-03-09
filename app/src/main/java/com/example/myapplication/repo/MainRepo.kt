package com.example.myapplication.repo

import com.example.myapplication.base.BaseDataSource
import com.example.myapplication.data.response.ApiResponse
import com.example.myapplication.helper.Resource
import com.example.myapplication.network.ApiDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class MainRepo @Inject constructor(private val apiDataSource: ApiDataSource) : BaseDataSource() {

    //Using coroutines flow to get the response from
    suspend fun getConvertedData(
        to: String
    ): Flow<Resource<ApiResponse>> {
        return flow {
            emit(safeApiCall { apiDataSource.getConvertedRate(to) })
        }.flowOn(Dispatchers.IO)
    }


}