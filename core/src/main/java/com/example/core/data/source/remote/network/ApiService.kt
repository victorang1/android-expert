package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.PopularMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<PopularMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDetailResponse>
}