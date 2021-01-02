package com.example.core.domain.repository

import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getMovieData(): Flow<Resource<List<Movie>>>
    suspend fun searchMovie(name: String): Flow<Resource<List<Movie>>>
}