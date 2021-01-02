package com.example.capstoneproject.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.FavoriteUseCase

class DetailViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    fun addToFavorite(movie: Movie) = favoriteUseCase.addFavorite(movie)

    fun deleteFromFavorite(movie: Movie) = favoriteUseCase.removeFavorite(movie)

    fun isFavorite(movieId: Int) = favoriteUseCase.checkIsFavorite(movieId).asLiveData()
}