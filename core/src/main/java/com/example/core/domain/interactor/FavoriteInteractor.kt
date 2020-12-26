package com.example.core.domain.interactor

import com.example.core.domain.repository.IFavoriteRepository
import com.example.core.domain.usecase.FavoriteUseCase

class FavoriteInteractor(private val favoriteRepository: IFavoriteRepository) : FavoriteUseCase {
}