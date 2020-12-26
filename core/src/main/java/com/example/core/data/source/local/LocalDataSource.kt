package com.example.core.data.source.local

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    fun updateMovie(movie: MovieEntity, status: Boolean) {
        movie.isFavorite = status
        movieDao.updateMovie(movie)
    }
}