package com.example.core.domain.interactor

import com.example.core.data.Resource
import com.example.core.domain.model.Favorite
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.IFavoriteRepository
import com.example.core.domain.usecase.FavoriteUseCase
import kotlinx.coroutines.flow.Flow

class FavoriteInteractor(private val favoriteRepository: IFavoriteRepository) : FavoriteUseCase {

    override fun addFavorite(movie: Movie) = favoriteRepository.insertToFavorite(movie)

    override fun removeFavorite(favorite: Favorite) =
        favoriteRepository.removeFromFavorite(favorite)

    override fun checkIsFavorite(movieId: Int): Flow<Boolean> =
        favoriteRepository.isFavorite(movieId)

    override fun getAllFavorite(): Flow<List<Favorite>> = favoriteRepository.getFavoriteData()
}