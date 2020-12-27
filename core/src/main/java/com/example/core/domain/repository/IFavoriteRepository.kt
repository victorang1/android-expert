package com.example.core.domain.repository

import com.example.core.domain.model.Favorite
import com.example.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    fun insertToFavorite(movie: Movie)
    fun removeFromFavorite(favorite: Favorite)
    fun searchFavorite(name: String): Flow<List<Favorite>>
    fun isFavorite(movieId: Int): Flow<Boolean>
    fun getFavoriteData(): Flow<List<Favorite>>
}