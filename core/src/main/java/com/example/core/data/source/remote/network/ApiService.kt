package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.PopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): PopularMovieResponse
}