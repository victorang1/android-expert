package com.example.core.domain.repository

import com.example.core.domain.model.Favorite
import com.example.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    fun insertToFavorite(movie: Movie)
    fun removeFromFavorite(movie: Movie)
    fun isFavorite(movieId: Int): Flow<Boolean>
    fun getFavoriteData(): Flow<List<Favorite>>
}