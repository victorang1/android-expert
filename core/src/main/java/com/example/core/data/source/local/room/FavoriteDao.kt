package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite where filmId=:movieId")
    fun isFavorite(movieId: Int): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM favorite where filmId=:id")
    suspend fun deleteFavorite(id: Int)
}