package com.example.capstoneproject.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.FavoriteUseCase
import com.example.core.domain.usecase.MovieUseCase

class DetailViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    fun addToFavorite(movie: Movie) = favoriteUseCase.addFavorite(movie)

    fun isFavorite(movieId: Int) = favoriteUseCase.checkIsFavorite(movieId).asLiveData()
}