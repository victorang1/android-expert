package com.example.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Favorite
import com.example.core.domain.usecase.FavoriteUseCase

class FavoriteViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    val favorites = favoriteUseCase.getAllFavorite().asLiveData()
}