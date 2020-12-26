package com.example.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.core.data.source.local.entity.Favorite
import com.example.core.data.source.local.entity.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * FROM favorite where title LIKE '%' || :title || '%'")
    fun filterFilms(title: String): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFromFavorite(favorite: Favorite)
}