package com.example.core.data.source.remote

import android.util.Log
import com.example.core.BuildConfig.API_KEY
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.PopularMovieItemResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val apiService: ApiService) {

    fun getPopularMovies(): Flow<ApiResponse<List<PopularMovieItemResponse>>> {
        return flow {
            try {
                val response = apiService.getPopularMovies(API_KEY)
                Log.d("<RESULT>", "getPopularMovies: " + Gson().toJson(response.results))
                if (response.results.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}