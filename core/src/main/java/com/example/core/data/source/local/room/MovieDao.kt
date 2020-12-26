package com.example.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.core.data.source.local.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMovies(): ArrayList<Movie>

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getMovieById(id: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)
}