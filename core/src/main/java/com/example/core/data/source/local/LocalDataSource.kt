package com.example.core.data.source.local

import com.example.core.data.source.local.entity.FavoriteEntity
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.room.FavoriteDao
import com.example.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class LocalDataSource(private val movieDao: MovieDao, private val favoriteDao: FavoriteDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getMovies()

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    fun getAllFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getFavorites()

    fun searchFavorites(name: String): Flow<List<FavoriteEntity>> = favoriteDao.searchFavorites(name)

    suspend fun insertFavorite(favorite: FavoriteEntity) = favoriteDao.insertFavorite(favorite)

    fun isFavorite(movieId: Int): Flow<FavoriteEntity> = favoriteDao.isFavorite(movieId).distinctUntilChanged()

    suspend fun removeFavorite(favorite: FavoriteEntity) = favoriteDao.deleteFavorite(favorite)
}