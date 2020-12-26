package com.example.capstoneproject.ui.detail

import androidx.lifecycle.ViewModel
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun setFavorite(movie: Movie, status: Boolean) = movieUseCase.setFavorite(movie, status)
}