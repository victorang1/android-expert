package com.example.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.core.data.source.local.entity.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getMovieById(id: Int): Flow<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)
}