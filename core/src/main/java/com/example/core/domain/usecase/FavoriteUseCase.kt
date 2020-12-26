package com.example.core.domain.usecase

import com.example.core.domain.model.Favorite
import com.example.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun addFavorite(movie: Movie)
    fun removeFavorite(favorite: Favorite)
    fun checkIsFavorite(movieId: Int): Flow<Boolean>
    fun getAllFavorite(): Flow<List<Favorite>>
}