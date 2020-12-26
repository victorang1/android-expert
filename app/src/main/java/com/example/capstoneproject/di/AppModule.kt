package com.example.capstoneproject.di

import com.example.core.data.repository.MovieRepositoryImpl
import com.example.core.domain.interactor.FavoriteInteractor
import com.example.core.domain.interactor.MovieInteractor
import com.example.core.domain.repository.IMovieRepository
import com.example.core.domain.usecase.FavoriteUseCase
import com.example.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    abstract fun provideFavoriteUseCase(favoriteInteractor: FavoriteInteractor): FavoriteUseCase
}