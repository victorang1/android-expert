package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.Favorite
import com.example.core.data.source.local.entity.Movie

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): ArrayList<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFromFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite where title LIKE '%' || :title || '%'")
    fun filterFilms(title: String, filmType: String): ArrayList<Movie>
}